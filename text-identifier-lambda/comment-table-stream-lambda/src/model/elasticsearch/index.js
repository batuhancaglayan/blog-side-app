const generateCommentDoc = (item) => {
    return {
        id: `${item.id.S}`,
        createdAt: `${item.createdAt.S}`,
        email: `${item.email.S}`,
        comment: `${item.comment.S}`,
        identifiedAt: `${item.identifiedAt.S}`,
        isActive: item.isActive.N
    }
}

module.exports = {
    generateCommentDoc,
}