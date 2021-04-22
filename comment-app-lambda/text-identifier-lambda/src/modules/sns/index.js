const AWS = require('aws-sdk');

const { config } = require('../../config');

let snsClient;

const getSnsClient = () => {
    if (!snsClient) {
        console.log('Loading snsClient');
        snsClient = new AWS.SNS({ region: config.region });
    }

    return snsClient;
}

module.exports = {
    getSnsClient
};