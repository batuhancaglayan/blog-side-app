const config = {
    region: process.env.REGION || '',
    elasticSearchNode: process.env.ELASTICSEARCH_NODE || '',
    logLevel: process.env.LOG_LEVEL || 'info',
}

module.exports = {
    config
};