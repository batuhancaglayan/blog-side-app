#!/usr/bin/env bash

if [[ $# -ge 2 ]]; then
export CDK_DEPLOY_ACCOUNT=$1
export CDK_DEPLOY_REGION=$2
shift; shift

echo '############################# build main app ###################################'
mvn -f ../comment-app clean install -DskipTests

echo '####################### text-identifier-lambda #################################'
(
    cd ../comment-app-lambda/text-identifier-lambda
    npm install;
)

echo '####################### dynamo-writer-lambda ###################################'
(
    cd ../comment-app-lambda/dynamo-writer-lambda
    npm install;
)

echo '####################### ses-writer-lambda ######################################'
(
    cd ../comment-app-lambda/ses-sender-lambda
    npm install;
)

echo '################## comment-table-stream-lambda #################################'
(
    cd ../comment-app-lambda/comment-table-stream-lambda
    npm install;
)

echo '###################### comment-app-cloud-infra #################################'
(
    cd comment-app-cloud-infra;
    npm install;
)

echo '############################ cdk bootstrap #####################################'
(
    cd comment-app-cloud-infra;
    npx cdk bootstrap aws://$CDK_DEPLOY_ACCOUNT/$CDK_DEPLOY_REGION "$@"
)

echo '############################ cdk deploy ########################################'
(
    cd comment-app-cloud-infra;
    npx cdk deploy --all "$@";
)

echo 'Deployment Completed'

exit $?
else
echo 1>&2 "Provide AWS account and region as first two args."
echo 1>&2 "Additional args are passed through to cdk deploy."
exit 1
fi
