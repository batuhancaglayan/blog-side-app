import * as cdk from '@aws-cdk/core';

import { NetworkStack } from './network-stack'

// import { EcsStack } from './ecs'

import { SesSenderStack } from './ses-sender-stack';

import { CommentWriterStack } from './comment-writer-stack';

import { CommentTextIdentifierStack } from './comment-text-identifier-stack'

import { DynamoStreamStack } from './dynamo-stream-stack';

export class CommentAppCloudInfraStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);
    
    const networkStack = new NetworkStack(this, 'AssigmentNetworkStack', {
      stackName: 'AssigmentNetworkStack',
      env: props?.env
    });
    
    const commentWriterStack = new CommentWriterStack(this, 'AssigmentCommentWriterStack', { 
      stackName: 'AssigmentCommentWriterStack',
      vpc: networkStack.vpc,
      env: props?.env
    });
    
    const sesSenderStack = new SesSenderStack(this, 'AssigmentSesSenderStack', { 
      stackName: 'AssigmentSesSenderStack',
      vpc: networkStack.vpc,
      env: props?.env
    });

    const dynamoStreamStack = new DynamoStreamStack(this, 'AssigmentDynamoStreamStack', { 
      stackName: 'AssigmentDynamoStreamStack',
      vpc: networkStack.vpc,
      env: props?.env,
      commentTable: commentWriterStack.commentTable
    }); 
    
    const commentTextIdentifierStack = new CommentTextIdentifierStack(this, 'AssigmentCommentTextIdentifierStack', {
      stackName: 'AssigmentCommentTextIdentifierStack',
      vpc: networkStack.vpc,
      env: props?.env,
      verifiedCommentSNS: commentWriterStack.verifiedCommentSNS,
      bannedCommentSNS: sesSenderStack.bannedCommentSNS,
    });
    
    // const ecsStack = new EcsStack(this, 'AssigmentEcs', { 
    //   vpc: networkStack.vpc,
    //   env: props?.env
    // });
  }
}
