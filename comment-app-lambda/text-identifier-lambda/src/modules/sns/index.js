const AWS = require('aws-sdk');

const { config } = require('../../config');

const { logger } = require('../../helper/logger');

let snsClient;

const getSnsClient = () => {
    if (!snsClient) {
        snsClient = new AWS.SNS({ region: config.region });
        logger.log({level: 'debug', message: 'SnsClient Initialized'});
    }

    return snsClient;
}

module.exports = {
    getSnsClient
};