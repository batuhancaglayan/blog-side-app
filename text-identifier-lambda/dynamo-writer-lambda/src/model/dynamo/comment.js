const generateCommentItem = (item) => {
    return {
        id: { S: `${item.id}` },
        createdAt: { N: `${item.createdAt}` },
        email: { S: `${item.email}` },
        comment: { S: `${item.comment}` },
        identifiedAt: { N: `${item.identifiedAt}` },
        isActive: { N: '1' },
    }
}

module.exports = {
    generateCommentItem,
}