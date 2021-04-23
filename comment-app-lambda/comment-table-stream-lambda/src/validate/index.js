const { dynamoStreamSchema } = require('../model/validation')

const { logger } = require('../helper/logger');

const validate = (fn) => {
    return async function (){
    
      const { dynamodb, eventName } = arguments[0];

      await dynamoStreamSchema.validateAsync(dynamodb);

      arguments[0] = { record: dynamodb.NewImage, eventName };

      try {
        return await fn.apply(this, arguments);
      } catch (error) {
        logger.log({level: 'error', message: err});
        throw err; // re-throw for middy
      }    
    };
};

module.exports = {
    validate,
}