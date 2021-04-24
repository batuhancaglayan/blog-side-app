const { dynamoStreamSchema } = require('../model/validation')

const validate = (fn) => {
    return async function (){
    
      const { dynamodb, eventName } = arguments[0];
      await dynamoStreamSchema.validateAsync(dynamodb);
      arguments[0] = { record: dynamodb.NewImage, eventName };

      return await fn.apply(this, arguments);   
    };
};

module.exports = {
    validate,
}