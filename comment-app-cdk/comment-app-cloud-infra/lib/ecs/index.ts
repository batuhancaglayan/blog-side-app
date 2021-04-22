import * as cdk from '@aws-cdk/core';
import * as ec2 from '@aws-cdk/aws-ec2';
import * as ecs from '@aws-cdk/aws-ecs';
import { DockerImageAsset } from '@aws-cdk/aws-ecr-assets';
import * as ecr from '@aws-cdk/aws-ecr';
import * as elbv2 from '@aws-cdk/aws-elasticloadbalancingv2';

import * as path from 'path';

export interface EcsStackProps extends cdk.StackProps {
    vpc: ec2.Vpc;
}

export class EcsStack extends cdk.Stack {
    
    constructor(scope: cdk.Construct, id: string, props: EcsStackProps) {
        super(scope, id, props);
        // console.log("environment variables " + JSON.stringify(process.env));
        
        const asset = new DockerImageAsset(this, 'MyBuildImage', {
            directory: path.join(__dirname, '../../docker-images') // path.basename('../../../../../text-identifier-query-service')
        });
        
        const vpc: ec2.Vpc = props.vpc;
        
        const cluster = new ecs.Cluster(this, 'Cluster', { vpc });

        const fargateTaskDefinition = new ecs.FargateTaskDefinition(this, 'TaskDef', {
            memoryLimitMiB: 512,
            cpu: 256,
        });

        const tempRepo = ecr.Repository.fromRepositoryName(this, asset.repository.repositoryName, asset.repository.repositoryName);

        console.log(asset.imageUri)

        fargateTaskDefinition.addContainer("MyContainer", {
            image: ecs.ContainerImage.fromEcrRepository(tempRepo, asset.imageUri.split(':')[1]),
            memoryLimitMiB: 512,
            portMappings: [{ containerPort: 8080 }],
            logging: ecs.LogDrivers.awsLogs({ streamPrefix: 'EventDemo' }),
        });

        const service = new ecs.FargateService(this, 'Service', {
            cluster,
            taskDefinition: fargateTaskDefinition,
            desiredCount: 2
        });

        const lb = new elbv2.ApplicationLoadBalancer(this, 'LB', { internetFacing: true, vpc });
        const listener = lb.addListener('Listener', { port: 80, defaultAction: elbv2.ListenerAction.fixedResponse(200) });
        
        listener.addTargets('ECS_TARGET', {
            priority: 1,
            port: 80,
            conditions: [
                elbv2.ListenerCondition.pathPatterns(['/deneme*']),
            ],
            targets: [service.loadBalancerTarget({
                containerName: 'MyContainer',
                containerPort: 8080
            })],
            healthCheck: {
                enabled: true,
                path: '/deneme/health',     
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
    