const { getSnsClient } = require('../../modules/sns');

const { logger } = require('../../helper/logger');

const publishSns = async ({ 
    topic,
    subject,
    event 
}) => {
    const snsClient = getSnsClient();

    logger.log({level: 'debug', message: `Comment with id: ${event.id} will send ${subject} topic.`});

    return (await snsClient.publish({
        Message: JSON.stringify({
            ...event,
            identifiedAt: new Date().getTime(),
        }),
        Subject: subject,
        TopicArn: topic,
    }).promise());
}

module.exports = {
    publishSns,
};