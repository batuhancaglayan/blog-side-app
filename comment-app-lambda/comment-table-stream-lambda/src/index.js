const { process } = require('./service/process-stream-data-service');

const { validate } = require('./validate')

const middy = require('@middy/core')
const sqsPartialBatchFailureMiddleware = require('@middy/sqs-partial-batch-failure')

const handler = async (event) => {
  const messageProcessingPromises = event.Records.map(processMessage)

  return Promise.allSettled(messageProcessingPromises)
}

const processMessage = validate(async (streamEvent) => {
    const aq = await process({ streamEvent });
    return aq;
});

const middyHandler = middy(handler)
middyHandler.use(sqsPartialBatchFailureMiddleware())

module.exports.handler = middyHandler