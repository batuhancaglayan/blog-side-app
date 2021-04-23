const { Client } = require('@elastic/elasticsearch');
const { createAWSConnection, awsGetCredentials } = require('@acuris/aws-es-connection');

const { config } = require('../../config')

const { logger } = require('../../helper/logger');

let esClient;

const getEsClient = async () => {

    if (!esClient){
        const awsCredentials = await awsGetCredentials()
        const AWSConnection = createAWSConnection(awsCredentials);
        
        esClient= new Client({
          ...AWSConnection,
          node: config.elasticSearchNode,
        });       

        logger.log({level: 'debug', message: 'EsClient Initialized'});
    }

    return esClient;
}

module.exports = {
    getEsClient,
};