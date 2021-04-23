const AWS = require("aws-sdk");

const { config } = require('../../config');

const { logger } = require('../../helper/logger');

let sesClient;

const getSesClient = () => {
    if (!sesClient) {
        sesClient = new AWS.SES({ region: config.region });
        logger.log({level: 'debug', message: 'SesClient Initialized'});
    }

    return sesClient;
}

module.exports = {
    getSesClient,
};