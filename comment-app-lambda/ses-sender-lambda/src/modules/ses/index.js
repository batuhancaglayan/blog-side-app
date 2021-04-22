const AWS = require("aws-sdk");

const { config } = require('../../config');

let sesClient;

const getSesClient = () => {
    if (!sesClient) {
        console.log('Loading sesClient');
        sesClient = new AWS.SES({ region: config.region });
    }

    return sesClient;
}

module.exports = {
    getSesClient,
};