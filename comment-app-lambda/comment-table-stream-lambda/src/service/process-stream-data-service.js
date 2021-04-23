const { commentDaoActions } = require('../dao/comment-dao-router');

const { STREAM_EVENT_TYPES } = require('../constant');

const { generateCommentDoc } = require('../model/elasticsearch')

const { md5Hash } = require('../utils/hash-util');

const { logger } = require('../helper/logger');

const process = async ({ streamEvent }) => {

    const { 
        record,
        eventName,
    } = streamEvent;

    const action = STREAM_EVENT_TYPES[eventName];
    if (action){
        const commentAcion = commentDaoActions[action];

        const doc = generateCommentDoc(record); 
        const index = md5Hash(doc.email);

        logger.log({level: 'debug', message: `Document: ${doc.id} will be ${action} to ES.`});

        return await commentAcion({index, doc });
    }

    return '';
}

module.exports = {
    process
}