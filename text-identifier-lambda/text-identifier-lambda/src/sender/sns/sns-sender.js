const { getSnsClient } = require('../../modules/sns')

class SnsSender {

    constructor({ topic, subject }) {
        this.topic = topic;
        this.subject = subject;
    }

    async send({ event }) {
        const { topic, subject } = this;

        const snsClient = getSnsClient();
        // https://docs.aws.amazon.com/AWSJavaScriptSDK/latest/AWS/SES.html#sendEmail-property
        await snsClient.publish({
            Message: JSON.stringify({
                ...event,
                identifiedAt = new Date(),
            }),
            Subject: subject,
            TopicArn: topic,
        }).promise();
    }
}

module.exports = SnsSender;