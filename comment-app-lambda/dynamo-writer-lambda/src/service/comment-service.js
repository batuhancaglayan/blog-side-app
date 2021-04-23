const { putItem } = require('../dao/comment-dao');

const { generateCommentItem } = require('../model/dynamo/comment');

const { logger } = require('../helper/logger');

const writeComment = async ({ item }) => {

    logger.log({level: 'debug', message: `Item: ${item.id} will be write.`});
    return putItem({ item: generateCommentItem(item) });
}

module.exports = {
    writeComment
};