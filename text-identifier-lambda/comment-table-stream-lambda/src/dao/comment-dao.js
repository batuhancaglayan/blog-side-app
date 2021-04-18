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

    const docs = await searchByDynamoRefAndEmail({ index, doc });

    if (docs.length != 1){
        throw Error(`Document not find with dynamoRefId: ${doc.dynamoRefId}`)
    }

    return (await esClient.update({
        index,
        id: docs[0]._id,
        body: { 
            doc: {
                comment: doc.comment,
                isActive: doc.isActive
            }
        },
    }));
}

const searchByDynamoRefAndEmail = async ({ index, doc }) => {

    const esClient = await getEsClient();

    const { body } = await esClient.search({
        index,
        body: {
            query: {
            bool: {
                must: [
                  {
                    match: {
                        dynamoRefId: doc.dynamoRefId,
                    }
                  },
                  {
                    match: {
                        email: doc.email,
                    }
                  }
                ]
            }
        }
    }
    });
    
    return body.hits.hits;
}

module.exports = {
    insertComment,
    updateComment
}