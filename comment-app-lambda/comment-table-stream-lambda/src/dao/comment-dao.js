const { getEsClient } = require('../modules/elasticsearch')

const insertComment = async ({ index, doc }) => {

    const esClient = await getEsClient();

    return (await esClient.create({
        index,
        id: doc.id,
        body: doc,
    }));
}

const updateComment = async ({ index, doc }) => {

    const esClient = await getEsClient();

    await getComment({ index, id: doc.id });

    return (await esClient.update({
        index,
        id: doc.id,
        body: { 
            doc: {
                comment: doc.comment,
                updatedAt: doc.updatedAt,
                isActive: doc.isActive
            }
        },
    }));
}

const getComment = async ({ index, id }) => {

    const esClient = await getEsClient();

    const { body } = await esClient.get({
        index,
        id,
    });
        
    return body._source;
}

module.exports = {
    insertComment,
    updateComment
}