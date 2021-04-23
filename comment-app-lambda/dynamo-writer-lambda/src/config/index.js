const config = {
    region: process.env.REGION || '',
    commentTableName: process.env.COMMENT_TABLE_NAME || '',
    logLevel: process.env.LOG_LEVEL || 'info',
}

module.exports = {
    config
};