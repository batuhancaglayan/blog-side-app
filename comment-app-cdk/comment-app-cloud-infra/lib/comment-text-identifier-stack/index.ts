import * as cdk from '@aws-cdk/core';

import * as lambda from '@aws-cdk/aws-lambda';
import { SqsEventSource } from '@aws-cdk/aws-lambda-event-sources';

import * as sns from '@aws-cdk/aws-sns';
import * as sqs from '@aws-cdk/aws-sqs';

import { Duration } from '@aws-cdk/core';

import * as ec2 from '@aws-cdk/aws-ec2';

import * as path from 'path';

export interface CommentTextIdentifierStackProps extends cdk.StackProps {
    vpc: ec2.Vpc;

    verifiedCommentSNS: sns.Topic;

    bannedCommentSNS: sns.Topic;
}

export class CommentTextIdentifierStack extends cdk.Stack {
    
    public readonly commentProcessSqs: sqs.Queue;

    constructor(scope: cdk.Construct, id: string, props: CommentTextIdentifierStackProps) {
        super(scope, id, props);
        
        const region = props.env?.region || '';
        const vpc: ec2.Vpc = props.vpc;
        const verifiedCommentSNS: sns.Topic = props.verifiedCommentSNS;
        const bannedCommentSNS: sns.Topic = props.bannedCommentSNS;
        
        const commentProcessSqsDlq = new sqs.Queue(this, 'CommentProcessSqsDlq', {
            queueName: 'CommentProcessSqsDlq',
            visibilityTimeout: Duration.seconds(60),     
            receiveMessageWaitTime: Duration.seconds(10),
            retentionPeriod: Duration.hours(1)
        });
        
        const commentProcessSqs = new sqs.Queue(this, 'CommentProcessSqs', {
            queueName: 'CommentProcessSqs',
            visibilityTimeout: Duration.seconds(60),     
            receiveMessageWaitTime: Duration.seconds(10),
            retentionPeriod: Duration.hours(1),
            deadLetterQueue: {
                maxReceiveCount: 1,
                queue: commentProcessSqsDlq
            }
        });
              
        const commentTextIdentifierLambda = new lambda.Function(this, 'CommentTextIdentifierLambda', { 
            functionName: "CommentTextIdentifierLambda",
            timeout: Duration.seconds(20),
            runtime: lambda.Runtime.NODEJS_12_X,
            handler: 'src/index.handler',
            memorySize: 256,
            description: '',
            code: lambda.Code.fromAsset(path.join(__dirname, '../../../../comment-app-lambda/text-identifier-lambda')),
            vpc,
            environment: {
                'REGION': region,
                'VERIFIED_COMMENT_SNS_TOPIC': verifiedCommentSNS.topicArn,
                'BANNED_COMMENT_SNS_TOPIC': bannedCommentSNS.topicArn,
            }
        });
        
        commentTextIdentifierLambda.addEventSource(new SqsEventSource(commentProcessSqs, {
            batchSize: 5,
            enabled: true,
            maxBatchingWindow: Duration.seconds(10),
        }));

        verifiedCommentSNS.grantPublish(commentTextIdentifierLambda);
        bannedCommentSNS.grantPublish(commentTextIdentifierLambda);

        this.commentProcessSqs = commentProcessSqs;
    }
}
