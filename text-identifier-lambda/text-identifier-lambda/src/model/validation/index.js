const Joi = require('joi');

const sqsRecordSchema = Joi.object({
    id: Joi.string().required(),
    createdAt: Joi.date().iso().required(),
    email: Joi.string().email({ minDomainSegments: 2, tlds: { allow: ['com', 'net'] } }).required(),
    comment: Joi.string().required(),
})

module.exports = {
    sqsRecordSchema,
}