const AWS = require("aws-sdk");

let dynamoClient;

const getDynamoClient = () => {
    if (!dynamoClient) {
        console.log('Loading dynamoClient');
        dynamoClient = new AWS.DynamoDB({ region: 'eu-central-1' });
    }

    return dynamoClient;
}

module.exports = {
    getDynamoClient
};