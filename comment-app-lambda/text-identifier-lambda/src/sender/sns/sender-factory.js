const { IDENTIFIED_RESULTS } = require('../../constant');

const { publishSns } = require('./sns-sender');

const { config } = require('../../config');

const snsSenders = {
    [IDENTIFIED_RESULTS.ALLOWEDCOMMENT]: async ({ event }) => {
        return await publishSns({
            topic: config.verifiedCommentSnsTopic,
            subject: `${IDENTIFIED_RESULTS.ALLOWEDCOMMENT}`,
            event
        })
    },
    [IDENTIFIED_RESULTS.DENIEDCOMMENT]: async ({ event }) => {
        return await publishSns({
            topic: config.bannedCommentSnsTopic,
            subject: `${IDENTIFIED_RESULTS.DENIEDCOMMENT}`,
            event
        })
    },
}

module.exports = {
    snsSenders,
}