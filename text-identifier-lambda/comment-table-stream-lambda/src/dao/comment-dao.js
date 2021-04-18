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

    return (await esClient.update({
        index,
        id: doc.id,
        body: { 
            doc
        },
    }));
}

module.exports = {
    insertComment,
    updateComment
}