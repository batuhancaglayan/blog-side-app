const { Client } = require('@elastic/elasticsearch');
const { createAWSConnection, awsGetCredentials } = require('@acuris/aws-es-connection');

const { config } = require('../../config')

let esClient;

const getEsClient = async () => {

    if (!esClient){
        const awsCredentials = await awsGetCredentials()
        const AWSConnection = createAWSConnection(awsCredentials);
        
        esClient= new Client({
          ...AWSConnection,
          node: config.elasticSearchNode,
        });       
    }

    return esClient;
}

module.exports = {
    getEsClient,
};