const { writeComment } = require('./service/comment-service');

const { validate } = require('./validate')

const middy = require('@middy/core')
const sqsPartialBatchFailureMiddleware = require('@middy/sqs-partial-batch-failure')

const handler = async (event) => {
  const messageProcessingPromises = event.Records.map(processMessage)

  return Promise.allSettled(messageProcessingPromises)
}

const processMessage = validate(async (record) => {
    return await writeComment({ item: record });
});

const middyHandler = middy(handler)
middyHandler.use(sqsPartialBatchFailureMiddleware())

module.exports.handler = middyHandler

// exports.handler = async (event) => {
//     //console.log('Received event:', JSON.stringify(event, null, 2));
//     for (const { messageId, body } of event.Records) {
//         console.log('SQS message %s: %j', messageId, body);

//         const snsBody = JSON.parse(body);
//         const message = JSON.parse(snsBody.Message);
        
//         const result = await commentService.writeComment({ item: message });
//     }

//     return `Successfully processed ${event.Records.length} messages.`;
// };
