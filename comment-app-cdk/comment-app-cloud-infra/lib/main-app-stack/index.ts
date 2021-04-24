import * as cdk from '@aws-cdk/core';

import * as ec2 from '@aws-cdk/aws-ec2';
import * as ecs from '@aws-cdk/aws-ecs';

import * as elbv2 from '@aws-cdk/aws-elasticloadbalancingv2';

import * as sqs from '@aws-cdk/aws-sqs';

import * as es from '@aws-cdk/aws-elasticsearch';

import * as dynamodb from '@aws-cdk/aws-dynamodb';

import * as iam from '@aws-cdk/aws-iam';

import { Duration } from '@aws-cdk/core';

export interface MainAppStackProps extends cdk.StackProps {
    vpc: ec2.Vpc;

    commentProcessSqs: sqs.Queue;

    commentAppElasticSearch: es.Domain;

    commentTable: dynamodb.Table;
}

export class MainAppStack extends cdk.Stack {
    
    constructor(scope: cdk.Construct, id: string, props: MainAppStackProps) {
        super(scope, id, props);
            
        const vpc: ec2.Vpc = props.vpc;
        const commentProcessSqs: sqs.Queue = props.commentProcessSqs;  
        const commentAppElasticSearch: es.Domain = props.commentAppElasticSearch;  
        const commentTable: dynamodb.Table = props.commentTable;
        
        const cluster = new ecs.Cluster(this, 'CommentAppCluster', { 
            vpc, 
            clusterName: 'CommentAppCluster'
        });

        /********************************************************************************************************/
        /**************  comment-app-command-service ECS Service & Task & Repo   ********************************/
        /********************************************************************************************************/

        const commentAppCommandServiceTaskDefRole = new iam.Role(this, 'CommentAppCommandServiceTaskDefRole', {
            assumedBy: new iam.ServicePrincipal('ecs-tasks.amazonaws.com'),
            managedPolicies: [
                iam.ManagedPolicy.fromAwsManagedPolicyName('AmazonSQSFullAccess'),
                iam.ManagedPolicy.fromAwsManagedPolicyName('AmazonDynamoDBFullAccess')
            ]
        })

        const commentAppCommandServiceTaskDef = new ecs.FargateTaskDefinition(this, 'CommentAppCommandServiceTaskDef', {           
            memoryLimitMiB: 1024,
            cpu: 512,
            taskRole: commentAppCommandServiceTaskDefRole
        });

        commentAppCommandServiceTaskDef.addContainer("CommentAppCommandServiceTaskContainer", {
            image: ecs.ContainerImage.fromAsset('../../comment-app/comment-app-api/comment-app-command-service'),
            memoryLimitMiB: 1024,
            portMappings: [{ containerPort: 8080 }],
            logging: ecs.LogDrivers.awsLogs({ streamPrefix: 'CommentAppCommandServiceLogs' }),
            environment: { 
                JAVA_OPTIONS: `
                -Dspring.profiles.active=production 
                -Dcomment.process.sqs=${commentProcessSqs.queueUrl}
                -Dcomment.table.dynamo=${commentTable.tableName}
                -Dregion=${props.env?.region}
                -Dlog.level=INFO`,
            },
        });

        const commentAppCommandServiceDef = new ecs.FargateService(this, 'CommentAppCommandServiceDef', {
            cluster,
            taskDefinition: commentAppCommandServiceTaskDef,
            desiredCount: 2,
            healthCheckGracePeriod: Duration.seconds(120)
        });

        /********************************************************************************************************/
        /*****************  comment-app-qery-service ECS Service & Task & Repo   ********************************/
        /********************************************************************************************************/

        const commentAppQueryServiceTaskDefRole = new iam.Role(this, 'CommentAppQueryServiceTaskDefRole', {
            assumedBy: new iam.ServicePrincipal('ecs-tasks.amazonaws.com')
        })

        commentAppElasticSearch.grantReadWrite(commentAppQueryServiceTaskDefRole);

        const commentAppQueryServiceTaskDef = new ecs.FargateTaskDefinition(this, 'CommentAppQueryServiceTaskDef', {           
            memoryLimitMiB: 1024,
            cpu: 512,
            taskRole: commentAppQueryServiceTaskDefRole
        })

        commentAppQueryServiceTaskDef.addContainer("CommentAppQueryServiceTaskContainer", {
            image: ecs.ContainerImage.fromAsset('../../comment-app/comment-app-api/comment-app-query-service'),
            memoryLimitMiB: 1024,
            portMappings: [{ containerPort: 8080 }],
            logging: ecs.LogDrivers.awsLogs({ streamPrefix: 'CommentAppQueryServiceLogs' }),
            environment: { 
                JAVA_OPTIONS: `
                -Dspring.profiles.active=production 
                -Dcomment.app.elasticsearch.endpoint=https://${commentAppElasticSearch.domainEndpoint}
                -Dregion=${props.env?.region}
                -Dlog.level=INFO`,
            },
        });

        const commentAppQueryServiceDef = new ecs.FargateService(this, 'CommentAppQueryServiceDef', {
            cluster,
            taskDefinition: commentAppQueryServiceTaskDef,
            desiredCount: 2,
            healthCheckGracePeriod: Duration.seconds(120)
        });

        /********************************************************************************************************/
        /*****************************  Application Load Balancer & Listener   **********************************/
        /********************************************************************************************************/

        const commentAppLb = new elbv2.ApplicationLoadBalancer(this, 'CommentAppLB', { internetFacing: true, vpc });
        const commentAppLbListener = commentAppLb.addListener('CommentAppLbListener', { 
            port: 80, 
            defaultAction: elbv2.ListenerAction.fixedResponse(404) 
        });
        
        commentAppLbListener.addTargets('CommentAppCommandServiceTarget', {
            priority: 1,
            port: 80,
            conditions: [
                elbv2.ListenerCondition.pathPatterns(['/command*']),
            ],
            targets: [commentAppCommandServiceDef.loadBalancerTarget({
                containerName: 'CommentAppCommandServiceTaskContainer',
                containerPort: 8080
            })],
            healthCheck: {
                enabled: true,
                path: '/command/health',     
            }
        });

        commentAppLbListener.addTargets('CommentAppQueryServiceTarget', {
            priority: 2,
            port: 80,
            conditions: [
                elbv2.ListenerCondition.pathPatterns(['/query*']),
            ],
            targets: [commentAppQueryServiceDef.loadBalancerTarget({
                containerName: 'CommentAppQueryServiceTaskContainer',
                containerPort: 8080
            })],
            healthCheck: {
                enabled: true,
                path: '/query/health',     
            }
        });       
    }
}
    