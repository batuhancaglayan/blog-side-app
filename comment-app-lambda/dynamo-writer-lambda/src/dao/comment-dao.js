const { getDynamoClient } = require('../modules/dynamo')

const { config } = require('../config');

const putItem = async ({ item }) => {
    const dynamoClient = await getDynamoClient();

    return (await dynamoClient.putItem({
        TableName: config.commentTableName,
        Item: item
    }).promise());
}

module.exports = {
    putItem
};