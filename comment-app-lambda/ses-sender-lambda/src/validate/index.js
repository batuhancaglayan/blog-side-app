const { sqsRecordSchema } = require('../model/validation')

const { logger } = require('../helper/logger');

const validate = (fn) => {
    return async function (){
    
      const record = arguments[0];
      const snsBody = JSON.parse(record.body);
      const message = JSON.parse(snsBody.Message);

      await sqsRecordSchema.validateAsync(message);

      arguments[0] = message;

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