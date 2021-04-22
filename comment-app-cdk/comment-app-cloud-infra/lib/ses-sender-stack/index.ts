import * as cdk from '@aws-cdk/core';

import * as lambda from '@aws-cdk/aws-lambda';
import { SqsEventSource } from '@aws-cdk/aws-lambda-event-sources';

import * as sns from '@aws-cdk/aws-sns';
import * as sqs from '@aws-cdk/aws-sqs';

import * as subscriptions from '@aws-cdk/aws-sns-subscriptions';

import { Duration } from '@aws-cdk/core';

import * as ec2 from '@aws-cdk/aws-ec2';

import * as path from 'path';

export interface SesSenderStackProps extends cdk.StackProps {
    vpc: ec2.Vpc;
}

export class SesSenderStack extends cdk.Stack {
    
    public readonly bannedCommentSNS: sns.Topic;

    constructor(scope: cdk.Construct, id: string, props: SesSenderStackProps) {
        super(scope, id, props);
        
        const region = props.env?.region || '';
        const vpc: ec2.Vpc = props.vpc;
        
        const bannedCommentSNS = new sns.Topic(this, 'BannedCommentSNS', {
            displayName: 'BannedCommentSns',
            topicName: 'BannedCommentSns'
        })
        
        const bannedCommentSqsDlq = new sqs.Queue(this, 'BannedCommentSqsDlq', {
            queueName: 'BannedCommentSqsDlq',
            visibilityTimeout: Duration.seconds(60),     
            receiveMessageWaitTime: Duration.seconds(10),
            retentionPeriod: Duration.hours(1)
        });
        
        const bannedCommentSqs = new sqs.Queue(this, 'BannedCommentSqs', {
            queueName: 'BannedCommentSqs',
            visibilityTimeout: Duration.seconds(60),     
            receiveMessageWaitTime: Duration.seconds(10),
            retentionPeriod: Duration.hours(1),
            deadLetterQueue: {
                maxReceiveCount: 1,
                queue: bannedCommentSqsDlq
            }
        });
        
        bannedCommentSNS.addSubscription(new subscriptions.SqsSubscription(bannedCommentSqs));
        
        const bannedCommentSesSenderLambda = new lambda.Function(this, 'BannedCommentSesSenderLambda', { 
            functionName: "BannedCommentSesSenderLambda",
            timeout: Duration.seconds(20),
            runtime: lambda.Runtime.NODEJS_12_X,
            handler: 'src/index.handler',
            memorySize: 256,
            description: '',
            code: lambda.Code.fromAsset(path.join(__dirname, '../../../../comment-app-lambda/ses-sender-lambda')),
            vpc,
            environment: {
                'REGION': region,
            }
        });
        
        bannedCommentSesSenderLambda.addEventSource(new SqsEventSource(bannedCommentSqs, {
            batchSize: 5,
            enabled: true,
            maxBatchingWindow: Duration.seconds(10),
        }));

        this.bannedCommentSNS = bannedCommentSNS;
    }
}
