import * as cdk from '@aws-cdk/core';

import * as lambda from '@aws-cdk/aws-lambda';
import { SqsEventSource } from '@aws-cdk/aws-lambda-event-sources';

import * as sns from '@aws-cdk/aws-sns';
import * as sqs from '@aws-cdk/aws-sqs';

import * as subscriptions from '@aws-cdk/aws-sns-subscriptions';

import { Duration } from '@aws-cdk/core';

import * as ec2 from '@aws-cdk/aws-ec2';

import * as dynamodb from '@aws-cdk/aws-dynamodb';

import * as path from 'path';

export interface CommentWriterStackProps extends cdk.StackProps {
    vpc: ec2.Vpc;
}

export class CommentWriterStack extends cdk.Stack {
    
    public readonly commentTable: dynamodb.Table;

    public readonly verifiedCommentSNS: sns.Topic;
    
    constructor(scope: cdk.Construct, id: string, props: CommentWriterStackProps) {
        super(scope, id, props);
        
        const region = props.env?.region || '';
        const vpc: ec2.Vpc = props.vpc;
        
        const commentTable = new dynamodb.Table(this, 'CommentTable', {
            tableName: 'CommentTable',
            partitionKey: { 
                name: 'id', type: dynamodb.AttributeType.STRING
            },
            stream: dynamodb.StreamViewType.NEW_AND_OLD_IMAGES,     
        });
        
        const verifiedCommentSNS = new sns.Topic(this, 'VerifiedCommentSNS', {
            displayName: 'VerifiedCommentSns',
            topicName: 'VerifiedCommentSns'
        })
        
        const verifiedCommentSqsDlq = new sqs.Queue(this, 'VerifiedCommentSqsDlq', {
            queueName: 'VerifiedCommentSqsDlq',
            visibilityTimeout: Duration.seconds(60),     
            receiveMessageWaitTime: Duration.seconds(10),
            retentionPeriod: Duration.hours(1)
        });
        
        const verifiedCommentSqs = new sqs.Queue(this, 'VerifiedCommentSqs', {
            queueName: 'VerifiedCommentSqs',
            visibilityTimeout: Duration.seconds(60),     
            receiveMessageWaitTime: Duration.seconds(10),
            retentionPeriod: Duration.hours(1),
            deadLetterQueue: {
                maxReceiveCount: 1,
                queue: verifiedCommentSqsDlq
            }
        });
        
        verifiedCommentSNS.addSubscription(new subscriptions.SqsSubscription(verifiedCommentSqs));
        
        const verifiedCommentDynamoWriterLambda = new lambda.Function(this, 'VerifiedCommentDynamoWriterLambda', { 
            functionName: "VerifiedCommentDynamoWriterLambda",
            timeout: Duration.seconds(20),
            runtime: lambda.Runtime.NODEJS_12_X,
            handler: 'src/index.handler',
            memorySize: 256,
            description: '',
            code: lambda.Code.fromAsset(path.join(__dirname, '../../../../comment-app-lambda/dynamo-writer-lambda')),
            vpc,
            environment: {
                'REGION': region,
                'COMMENT_TABLE_NAME': commentTable.tableName,
            }
        });
        
        verifiedCommentDynamoWriterLambda.addEventSource(new SqsEventSource(verifiedCommentSqs, {
            batchSize: 5, 
            enabled: true,
            maxBatchingWindow: Duration.seconds(10),
        }));
        
        commentTable.grantReadWriteData(verifiedCommentDynamoWriterLambda);
        
        this.commentTable = commentTable;
        this.verifiedCommentSNS = verifiedCommentSNS;
    }
}
