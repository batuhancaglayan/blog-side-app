const { IDENTIFIED_RESULTS } = require('../../constant');

const { publishSns } = require('./sns-sender');

const snsSenders = {
    [IDENTIFIED_RESULTS.ALLOWEDCOMMENT]: async ({ event }) => {
        await publishSns({
            topic: 'arn:aws:sns:eu-central-1:270045217160:text-idetification-writer-topic',
            subject: `${IDENTIFIED_RESULTS.ALLOWEDCOMMENT}`,
            event
        })
    },
    [IDENTIFIED_RESULTS.DENIEDCOMMENT]: async ({ event }) => {
        await publishSns({
            topic: 'arn:aws:sns:eu-central-1:270045217160:text-idetification-writer-topic',
            subject: `${IDENTIFIED_RESULTS.DENIEDCOMMENT}`,
            event
        })
    },
}

module.exports = {
    snsSenders,
}