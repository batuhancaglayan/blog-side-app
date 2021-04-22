const AWS = require("aws-sdk");

const { config } = require('../../config');

let dynamoClient;

const getDynamoClient = () => {
    if (!dynamoClient) {
        dynamoClient = new AWS.DynamoDB({ region: config.region });
    }

    return dynamoClient;
}

module.exports = {
    getDynamoClient
};