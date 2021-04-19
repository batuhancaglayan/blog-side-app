const { send } = require('../sender/ses');

const { craeteIdentificationFailMail } = require('../model/email/identification-fail');

const sendEmail = async ({ message }) => {

    const destination = message.email;
    const mailContent = await craeteIdentificationFailMail(destination);

    return (await send({ mailContent }));
}

module.exports = {
    sendEmail,
};