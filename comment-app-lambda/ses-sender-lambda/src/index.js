const { sendEmail } = require('./service/ses-service');

const { validate } = require('./helper/validation');

const { errorHandler } = require('./helper/error-handler');

const middy = require('@middy/core');
const sqsPartialBatchFailureMiddleware = require('@middy/sqs-partial-batch-failure');

const handler = async (event) => {
  const messageProcessingPromises = event.Records.map(processMessage)

  return Promise.allSettled(messageProcessingPromises)
}

const processMessage = errorHandler(validate(async (record) => {
    return await sendEmail({ message: record });
}));

const middyHandler = middy(handler)
middyHandler.use(sqsPartialBatchFailureMiddleware())

module.exports.handler = middyHandler