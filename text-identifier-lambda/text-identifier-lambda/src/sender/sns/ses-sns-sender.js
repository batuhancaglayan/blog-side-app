const SnsSender = require('./sns-sender')

class SesSnsSender extends SnsSender {

    constructor({ topic }) {
        super({
            topic,
            subject: 'un-success identifaction'
        })
    }

    async send({ event }) {
        const { subject } = this

        const sesEvent = {
            from: 'batuhanaskyour@gmail.com',
            to: event.requesterMail,
            subject: subject,
            body: 'dddd yanlis'
        };

        await super.send({ event: sesEvent });
    }
}

module.exports = SesSnsSender;