const DynamoWriterSnsSender = require('./dynamo-writer-sns-sender');
const SesSnsSender = require('./ses-sns-sender')

const sesSnsSenderType = {
    DYNAMOSNSSENDER: 1,
    SESSNSSENDER: 2
}

const getSender = (senderType) => {

    let sender;
    switch (senderType) {
        case sesSnsSenderType.DYNAMOSNSSENDER:
            sender = new DynamoWriterSnsSender({ topic: 'arn:aws:sns:eu-central-1:270045217160:text-idetification-writer-topic' });
            break;
        case sesSnsSenderType.SESSNSSENDER:
            sender = new SesSnsSender({ topic: 'arn:aws:sns:eu-central-1:270045217160:text-idetification-writer-topic' });
            break;
        default:
            break;
    }

    return sender;
}

module.exports = {
    getSender,
    sesSnsSenderType,
};