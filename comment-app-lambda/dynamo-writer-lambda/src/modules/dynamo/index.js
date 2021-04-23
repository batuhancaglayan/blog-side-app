const AWS = require("aws-sdk");

const { config } = require('../../config');

const { logger } = require('../../helper/logger');

let dynamoClient;

const getDynamoClient = () => {
    if (!dynamoClient) {
        dynamoClient = new AWS.DynamoDB({ region: config.region });
        logger.log({level: 'debug', message: 'DynamoClient Initialized'});
    }

    return dynamoClient;
}

module.exports = {
    getDynamoClient
};