const generateCommentItem = (item) => {
    return {
        id: { S: `${item.id}` },
        createdAt: { S: `${item.createdAt}` },
        email: { S: `${item.email}` },
        comment: { S: `${item.comment}` },
        identifiedAt: { S: `${item.identifiedAt}` },
        isActive: { N: '1' },
    }
}

module.exports = {
    generateCommentItem,
}