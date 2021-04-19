const AWS = require("aws-sdk");

let sesClient;

const getSesClient = () => {
    if (!sesClient) {
        console.log('Loading sesClient');
        sesClient = new AWS.SES({ region: 'eu-central-1' });
    }

    return sesClient;
}

module.exports = {
    getSesClient,
};