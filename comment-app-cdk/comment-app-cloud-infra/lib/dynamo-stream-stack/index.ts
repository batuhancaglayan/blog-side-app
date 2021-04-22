import * as cdk from '@aws-cdk/core';

import * as lambda from '@aws-cdk/aws-lambda';
import { DynamoEventSource, SqsDlq } from '@aws-cdk/aws-lambda-event-sources';

import * as sns from '@aws-cdk/aws-sns';
import * as sqs from '@aws-cdk/aws-sqs';

import * as subscriptions from '@aws-cdk/aws-sns-subscriptions';

import { Duration } from '@aws-cdk/core';

import * as ec2 from '@aws-cdk/aws-ec2';

import * as dynamodb from '@aws-cdk/aws-dynamodb';

import * as es from '@aws-cdk/aws-elasticsearch';

import * as path from 'path';

export interface DynamoStreamStackProps extends cdk.StackProps {
  vpc: ec2.Vpc;
  
  commentTable: dynamodb.Table;
}

export class DynamoStreamStack extends cdk.Stack {
  
  public readonly commentAppElasticSearch: es.Domain;
  
  constructor(scope: cdk.Construct, id: string, props: DynamoStreamStackProps) {
    super(scope, id, props);
    
    const region = props.env?.region || '';
    const vpc: ec2.Vpc = props.vpc; 
    const commentTable: dynamodb.Table = props.commentTable;
    
    const commentAppElasticSearch = new es.Domain(this, 'CommentAppElasticSearch', {
      domainName: 'comment-app-search-a',
      version: es.ElasticsearchVersion.V7_7,
      // vpc,
      // zoneAwareness: {
      //   enabled: true,
      // },
      enforceHttps: true, 
      capacity: {
        dataNodeInstanceType: 't3.small.elasticsearch',
        dataNodes: 1,
        // dataNodes: 1
        // dataNodes: 2
        // masterNodes: 1,
      },
      ebs: {
        volumeType: ec2.EbsDeviceVolumeType.GENERAL_PURPOSE_SSD,
        volumeSize: 20
      },
      // zoneAwareness: {
      //   availabilityZoneCount: 2
      // },
      // logging: {
      //   slowSearchLogEnabled: true,
      //   appLogEnabled: true,
      //   slowIndexLogEnabled: true,
      // },        
    });
    
    const dynamoStreamElasticsearchWriterLambda = new lambda.Function(this, 'DynamoStreamElasticsearchWriterLambda', { 
      functionName: "DynamoStreamElasticsearchWriterLambda",
      timeout: Duration.seconds(60),
      runtime: lambda.Runtime.NODEJS_12_X,
      handler: 'src/index.handler',
      memorySize: 256,
      description: '',
      code: lambda.Code.fromAsset(path.join(__dirname, '../../../../comment-app-lambda/comment-table-stream-lambda')),
      vpc,
      environment: {
        'REGION': region,
        'ELASTICSEARCH_NODE': commentAppElasticSearch.domainEndpoint,
      }
    });

    commentAppElasticSearch.grantReadWrite(dynamoStreamElasticsearchWriterLambda);
    
    const dynamoStreamElasticsearchWriterDlq = new sqs.Queue(this, 'DynamoStreamElasticsearchWriterDlq', {
      queueName: 'DynamoStreamElasticsearchWriterDlq',    
      retentionPeriod: Duration.hours(1)
    });
    
    dynamoStreamElasticsearchWriterLambda.addEventSource(new DynamoEventSource(commentTable, {
      startingPosition: lambda.StartingPosition.TRIM_HORIZON,
      batchSize: 5,
      maxBatchingWindow: Duration.seconds(20),
      bisectBatchOnError: false,
      onFailure: new SqsDlq(dynamoStreamElasticsearchWriterDlq),
      retryAttempts: 10
    }));
    
    this.commentAppElasticSearch = commentAppElasticSearch;
  }
}
