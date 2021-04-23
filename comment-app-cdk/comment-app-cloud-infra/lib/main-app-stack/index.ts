import * as cdk from '@aws-cdk/core';

import * as ec2 from '@aws-cdk/aws-ec2';
import * as ecs from '@aws-cdk/aws-ecs';

import { DockerImageAsset } from '@aws-cdk/aws-ecr-assets';

import * as ecr from '@aws-cdk/aws-ecr';

import * as elbv2 from '@aws-cdk/aws-elasticloadbalancingv2';

import * as sqs from '@aws-cdk/aws-sqs';

import * as es from '@aws-cdk/aws-elasticsearch';

import * as iam from '@aws-cdk/aws-iam';

import * as path from 'path';

export interface MainAppStackProps extends cdk.StackProps {
    vpc: ec2.Vpc;

    commentProcessSqs: sqs.Queue;

    commentAppElasticSearch: es.Domain;
}
//-Dspring.profiles.active=production
export class MainAppStack extends cdk.Stack {
    
    constructor(scope: cdk.Construct, id: string, props: MainAppStackProps) {
        super(scope, id, props);
            
        const vpc: ec2.Vpc = props.vpc;
        const commentProcessSqs: sqs.Queue = props.commentProcessSqs;  
        const commentAppElasticSearch: es.Domain = props.commentAppElasticSearch;  
        
        const cluster = new ecs.Cluster(this, 'CommentAppCluster', { 
            vpc, 
            clusterName: 'CommentAppCluster'
        });

        /********************************************************************************************************/
        /**************  comment-app-command-service ECS Service & Task & Repo   ********************************/
        /********************************************************************************************************/

        const commentAppCommandServiceAsset = new DockerImageAsset(this, 'CommentAppCommandService', {
            directory: path.join(__dirname, '../../../../comment-app/comment-app-api/comment-app-command-service')
        });

        const commentAppCommandServiceTaskDefRole = new iam.Role(this, 'CommentAppCommandServiceTaskDefRole', {
            assumedBy: new iam.ServicePrincipal('ecs-tasks.amazonaws.com'),
            managedPolicies: [
                iam.ManagedPolicy.fromAwsManagedPolicyName('AmazonSQSFullAccess'),
                iam.ManagedPolicy.fromAwsManagedPolicyName('AmazonDynamoDBFullAccess')
            ]
        })

        const commentAppCommandServiceTaskDef = new ecs.FargateTaskDefinition(this, 'CommentAppCommandServiceTaskDef', {           
            memoryLimitMiB: 512,
            cpu: 256,
            taskRole: commentAppCommandServiceTaskDefRole
        });

        const commentAppCommandServiceRepo = ecr.Repository.fromRepositoryName(
            this, 
            commentAppCommandServiceAsset.repository.repositoryName, 
            commentAppCommandServiceAsset.repository.repositoryName
        );

        commentAppCommandServiceTaskDef.addContainer("CommentAppCommandServiceTaskContainer", {
            image: ecs.ContainerImage.fromEcrRepository(
                commentAppCommandServiceRepo, 
                commentAppCommandServiceAsset.imageUri.split(':')[1]
            ),
            memoryLimitMiB: 512,
            portMappings: [{ containerPort: 8080 }],
            logging: ecs.LogDrivers.awsLogs({ streamPrefix: 'EventDemo' }),
            environment: { 
                JAVA_OPTIONS: `-Dspring.profiles.active=production -Dcomment.process.sqs=${commentProcessSqs.queueUrl}`,
            },
        });

        const commentAppCommandServiceDef = new ecs.FargateService(this, 'CommentAppCommandServiceDef', {
            cluster,
            taskDefinition: commentAppCommandServiceTaskDef,
            desiredCount: 2
        });

        /********************************************************************************************************/
        /*****************  comment-app-qery-service ECS Service & Task & Repo   ********************************/
        /********************************************************************************************************/

        const commentAppQueryServiceAsset = new DockerImageAsset(this, 'CommentAppQueryService', {
            directory: path.join(__dirname, '../../../../comment-app/comment-app-api/comment-app-query-service')
        });

        const commentAppQueryServiceTaskDefRole = new iam.Role(this, 'CommentAppQueryServiceTaskDefRole', {
            assumedBy: new iam.ServicePrincipal('ecs-tasks.amazonaws.com')
        })

        commentAppElasticSearch.grantRead(commentAppQueryServiceTaskDefRole);

        const commentAppQueryServiceTaskDef = new ecs.FargateTaskDefinition(this, 'CommentAppQueryServiceTaskDef', {           
            memoryLimitMiB: 512,
            cpu: 256,
            taskRole: commentAppQueryServiceTaskDefRole
        });

        const commentAppQueryServiceRepo = ecr.Repository.fromRepositoryName(
            this, 
            commentAppQueryServiceAsset.repository.repositoryName, 
            commentAppQueryServiceAsset.repository.repositoryName
        );

        commentAppQueryServiceTaskDef.addContainer("CommentAppQueryServiceTaskContainer", {
            image: ecs.ContainerImage.fromEcrRepository(
                commentAppQueryServiceRepo, 
                commentAppQueryServiceAsset.imageUri.split(':')[1]
            ),
            memoryLimitMiB: 512,
            portMappings: [{ containerPort: 8080 }],
            logging: ecs.LogDrivers.awsLogs({ streamPrefix: 'EventDemo' }),
            environment: { 
                JAVA_OPTIONS: `
                -Dspring.profiles.active=production 
                -Dcomment.app.elasticsearch.endpoint=${commentAppElasticSearch.env}
                -Dregion=${props.env?.region}`,
            },
        });

        const commentAppQueryServiceDef = new ecs.FargateService(this, 'CommentAppQueryServiceDef', {
            cluster,
            taskDefinition: commentAppQueryServiceTaskDef,
            desiredCount: 2
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
    