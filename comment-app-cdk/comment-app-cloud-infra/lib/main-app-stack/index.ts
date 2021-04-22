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
        
        const commentAppCommandServiceAsset = new DockerImageAsset(this, 'CommentAppCommandService', {
            directory: path.join(__dirname, '../../../../comment-app/comment-app-api/comment-app-command-service')
        });
        
        const vpc: ec2.Vpc = props.vpc;
        const commentProcessSqs: sqs.Queue = props.commentProcessSqs;  
        const commentAppElasticSearch: es.Domain = props.commentAppElasticSearch;  
        
        const cluster = new ecs.Cluster(this, 'CommentAppCluster', { 
            vpc, 
            clusterName: 'CommentAppCluster'
        });

        const commentAppCommandServiceTaskDefRole = new iam.Role(this, 'CommentAppCommandServiceTaskDefRole', {
            assumedBy: new iam.ServicePrincipal('ecs-tasks.amazonaws.com'),
            managedPolicies: [
                iam.ManagedPolicy.fromAwsManagedPolicyName('AmazonSQSFullAccess')
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
            serviceName: 'CommentAppCommandServiceDef',
            cluster,
            taskDefinition: commentAppCommandServiceTaskDef,
            desiredCount: 2
        });

        const commentAppLb = new elbv2.ApplicationLoadBalancer(this, 'CommentAppLB', { internetFacing: true, vpc });
        const commentAppLbListener = commentAppLb.addListener('CommentAppLbListener', { 
            port: 80, 
            defaultAction: elbv2.ListenerAction.fixedResponse(404) 
        });
        
        commentAppLbListener.addTargets('CommentAppCommandServiceTarget', {
            targetGroupName: 'CommentAppCommandServiceTarget',
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
        
        // const cluster = new ecs.Cluster(this, 'Cluster', { vpc });
        
        // const fargateTaskDefinition = new ecs.FargateTaskDefinition(this, 'TaskDef', {
        //     memoryLimitMiB: 512,
        //     cpu: 256,
        // });
        
        // const tempRepo = ecr.Repository.fromRepositoryName(this, 
        //     asset.repository.repositoryName,
        //     asset.repository.repositoryName,
        //     );
            
        //     fargateTaskDefinition.addContainer("MyContainer", {
        //         image: ecs.ContainerImage.fromEcrRepository(tempRepo),
        //         memoryLimitMiB: 512,
        //         portMappings: [{ containerPort: 8080 }],
        //         logging: ecs.LogDrivers.awsLogs({ streamPrefix: 'EventDemo' }),
        //     });
            
        //     const service = new ecs.FargateService(this, 'Service', {
        //         cluster,
        //         taskDefinition: fargateTaskDefinition,
        //         desiredCount: 2
        //     });
            
        //     const lb = new elbv2.ApplicationLoadBalancer(this, 'LB', { vpc, internetFacing: true });
        //     const listener = lb.addListener('Listener', { port: 80, defaultAction: elbv2.ListenerAction.fixedResponse(200) });
            
        //     listener.addTargets('ECS_TARGET', {
        //         priority: 1,
        //         port: 80,
        //         conditions: [
        //             elbv2.ListenerCondition.pathPatterns(['/deneme*']),
        //         ],
        //         targets: [service.loadBalancerTarget({
        //             containerName: 'MyContainer',
        //             containerPort: 8080
        //         })],
        //         healthCheck: {
        //             enabled: true,
        //             path: '/deneme/health',     
        //         }
        //     });
        }
    }
    