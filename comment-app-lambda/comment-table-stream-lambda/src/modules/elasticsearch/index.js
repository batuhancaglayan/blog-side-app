const { Client } = require('@elastic/elasticsearch');
const { createAWSConnection, awsGetCredentials } = require('@acuris/aws-es-connection')

let esClient;

const getEsClient = async () => {

    if (!esClient){
        const awsCredentials = await awsGetCredentials()
        const AWSConnection = createAWSConnection(awsCredentials);
        
        esClient= new Client({
          ...AWSConnection,
          node: 'https://search-comment-search-wv7tt6zswrmjwxqvfkk7xiqefm.eu-central-1.es.amazonaws.com',
        });       
    }

    return esClient;
}

module.exports = {
    getEsClient,
};