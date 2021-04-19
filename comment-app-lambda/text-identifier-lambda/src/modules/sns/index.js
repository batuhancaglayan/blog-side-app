const AWS = require('aws-sdk');

let snsClient;

const getSnsClient = () => {
    if (!snsClient) {
        console.log('Loading snsClient');
        snsClient = new AWS.SNS({ region: 'eu-central-1' });
    }

    return snsClient;
}

module.exports = {
    getSnsClient
};