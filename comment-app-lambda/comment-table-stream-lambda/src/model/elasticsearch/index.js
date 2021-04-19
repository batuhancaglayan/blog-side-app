const generateCommentDoc = (item) => {
    return {
        id: `${item.id.S}`,
        email: `${item.email.S}`,
        comment: `${item.comment.S}`,
        createdAt: `${item.createdAt.N}`,
        updatedAt: `${item.updatedAt.N}`,
        identifiedAt: `${item.identifiedAt.N}`,
        isActive: item.isActive.N
    }
}

module.exports = {
    generateCommentDoc,
}