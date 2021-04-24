#!/usr/bin/env bash

if [[ $# -ge 2 ]]; then
    export CDK_DEPLOY_ACCOUNT=$1
    export CDK_DEPLOY_REGION=$2
    shift; shift
    
    echo '############################# build main app ###################################'
    # mvn -f ../comment-app clean install 

    echo '####################### text-identifier-lambda #################################'
    npm install ../comment-app-lambda/text-identifier-lambda

    echo '####################### dynamo-writer-lambda ###################################'
    npm install ../comment-app-lambda/dynamo-writer-lambda

    echo '####################### ses-writer-lambda ######################################'
    npm install ../comment-app-lambda/ses-sender-lambda

    echo '################## comment-table-stream-lambda #################################'
    npm install ../comment-app-lambda/comment-table-stream-lambda

    echo '###################### comment-app-cloud-infra #################################'
    npm install ../comment-app-lambda/comment-app-cloud-infra

    echo '############################ cdk bootstrap #####################################'
    (
        cd comment-app-cloud-infra; 
        cdk bootstrap aws://$CDK_DEPLOY_ACCOUNT/$CDK_DEPLOY_REGION
    )
    
    echo '############################ cdk deploy ########################################'
    (
        cd comment-app-cloud-infra; 
        cdk deploy --all;
    )

    echo 'Deployment Completed'

    exit $?
else
    echo 1>&2 "Provide AWS account and region as first two args."
    echo 1>&2 "Additional args are passed through to cdk deploy."
    exit 1
fi
