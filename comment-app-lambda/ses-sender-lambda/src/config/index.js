const config = {
    region: process.env.REGION || '',
    logLevel: process.env.LOG_LEVEL || 'info',
    senderMail: process.env.SENDER_MAIL || 'example@gmail.com',
    account: process.env.ACCOUNT || '',
}

module.exports = {
    config
};