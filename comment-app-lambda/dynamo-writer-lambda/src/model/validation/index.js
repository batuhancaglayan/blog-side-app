const Joi = require('joi');

const sqsRecordSchema = Joi.object({
    id: Joi.string().required(),
    createdAt: Joi.number().required(),
    identifiedAt: Joi.number().required(),
    email: Joi.string().email({ minDomainSegments: 2, tlds: { allow: ['com', 'net'] } }).required(),
    comment: Joi.string().required(),
})

module.exports = {
    sqsRecordSchema,
}