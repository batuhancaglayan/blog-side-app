const { getSesClient } = require('../../modules/ses');

const send = async ({ mailContent }) => {

    const sesClient = await getSesClient();
    return (await sesClient.sendEmail(mailContent).promise());
}

module.exports = {
    send,
}