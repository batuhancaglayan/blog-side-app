const { getSnsClient } = require('../../modules/sns')

const publishSns = async ({ 
    topic,
    subject,
    event 
}) => {
    const snsClient = getSnsClient();

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