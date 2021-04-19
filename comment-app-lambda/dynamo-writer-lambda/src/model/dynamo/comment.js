const generateCommentItem = (item) => {
    return {
        id: { S: `${item.id}` },
        email: { S: `${item.email}` },
        comment: { S: `${item.comment}` },
        createdAt: { N: `${item.createdAt}` },
        updatedAt: { N: `${item.createdAt}` },
        identifiedAt: { N: `${item.identifiedAt}` },
        isActive: { N: '1' },
    }
}

module.exports = {
    generateCommentItem,
}