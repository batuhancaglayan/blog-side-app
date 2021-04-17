const { sqsRecordSchema } = require('../model/validation')

const validate = (fn) => {
    return async function (){
    
      const record = arguments[0];
      const snsBody = JSON.parse(record.body);
      const message = JSON.parse(snsBody.Message);

      await sqsRecordSchema.validateAsync(message);

      arguments[0] = message;
      return await fn.apply(this, arguments);
    };
};

module.exports = {
    validate,
}