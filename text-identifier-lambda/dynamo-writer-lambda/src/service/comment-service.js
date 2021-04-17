const { putItem } = require('../dao/comment-dao');

const { generateCommentItem } = require('../model/dynamo/comment');

const writeComment = async ({ item }) => {
    return putItem({ item: generateCommentItem(item) });
}

module.exports = {
    writeComment
};