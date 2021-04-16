const { getDynamoClient } = require('../modules/dynamo')

class CommentDao {

    async write({ item }) {

        const dynamoClient = await getDynamoClient();

        dynamoClient
    }
}

module.exports = CommentDao;