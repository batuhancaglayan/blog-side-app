const Joi = require('joi');

const dynamoStreamSchema = Joi.object({
    NewImage: Joi.object().keys({
        id: Joi.object().keys({
            S: Joi.string().required(),
        }),
        comment: Joi.object().keys({
            S: Joi.string().required(),
        }),
        email: Joi.object().keys({
            S: Joi.string().required(),
        }),
        createdAt: Joi.object().keys({
            N: Joi.number().required(),
        }),
        updatedAt: Joi.object().keys({
            N: Joi.number().required(),
        }),
        identifiedAt: Joi.object().keys({
            N: Joi.number().required(),
        }),
        isActive: Joi.object().keys({
            N: Joi.number().required(),
        }),
    })
}).unknown(true);

module.exports = {
    dynamoStreamSchema,
}
