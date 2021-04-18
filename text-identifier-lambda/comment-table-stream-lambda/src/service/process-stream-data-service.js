const { commentDaoActions } = require('../dao/comment-dao-router');

const { STREAM_EVENT_TYPES } = require('../constant');

const { generateCommentDoc } = require('../model/elasticsearch')

const process = async ({ streamEvent }) => {

    const { 
        record,
        eventName,
    } = streamEvent;

    const action = STREAM_EVENT_TYPES[eventName];
    if (action){
        const commentAcion = commentDaoActions[action];

        const doc = generateCommentDoc(record); 
        return await commentAcion({index: 'comment',  doc });
    }

    return '';
}

module.exports = {
    process
}