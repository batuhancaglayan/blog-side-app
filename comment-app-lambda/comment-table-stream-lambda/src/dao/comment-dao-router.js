const { STREAM_EVENT_TYPES } = require('../constant');

const { 
    insertComment,
    updateComment,
 } = require('./comment-dao')

const commentDaoActions = {
    [STREAM_EVENT_TYPES.INSERT]: insertComment,
    [STREAM_EVENT_TYPES.MODIFY]: updateComment,
}

module.exports = {
    commentDaoActions,
}