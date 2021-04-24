const { sqsRecordSchema } = require('../model/validation')

const validate = (fn) => {
    return async function (){

        const record = arguments[0];
        const recordBody = JSON.parse(record.body);
        await sqsRecordSchema.validateAsync(recordBody);
        arguments[0] = recordBody;

        return await fn.apply(this, arguments);
    };
};

module.exports = {
    validate,
}