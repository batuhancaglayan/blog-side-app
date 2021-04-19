const { getDynamoClient } = require('../modules/dynamo')

const putItem = async ({ item }) => {
    const dynamoClient = await getDynamoClient();

    return (await dynamoClient.putItem({
        TableName: 'comment-table',
        Item: item
    }).promise());
}

module.exports = {
    putItem
};