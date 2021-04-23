const { sqsRecordSchema } = require('../model/validation')

const { logger } = require('../helper/logger');

const validate = (fn) => {
    return async function (){

        const record = arguments[0];
        const recordBody = JSON.parse(record.body);
        await sqsRecordSchema.validateAsync(recordBody);
        arguments[0] = recordBody;

      try{
        return await fn.apply(this, arguments);
      }catch(err){
        logger.log({level: 'error', message: err});
        throw err; // re-throw for middy
      }
    };
};

module.exports = {
    validate,
}