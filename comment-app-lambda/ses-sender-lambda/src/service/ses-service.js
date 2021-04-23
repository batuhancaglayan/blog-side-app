const { send } = require('../sender/ses');

const { craeteIdentificationFailMail } = require('../model/email/identification-fail');

const { logger } = require('../helper/logger');

const sendEmail = async ({ message }) => {

    const destination = message.email;
    const mailContent = await craeteIdentificationFailMail(destination);

    logger.log({level: 'debug', message: `Mail will send to ${destination}.`});

    return (await send({ mailContent }));
}

module.exports = {
    sendEmail,
};