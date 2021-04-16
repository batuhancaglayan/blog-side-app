const {
    getSender,
    sesSnsSenderType,
} = require('../sender/sns/sns-sender-factory')

class IdentifierService {

    constructor({ textIdentifier }) {
        this.textIdentifier = textIdentifier;
    }

    async identify({ identifyRequest }) {
        const { textIdentifier } = this;

        const result = await textIdentifier.process({ textContent: identifyRequest.comment });

        const senderType = result ? sesSnsSenderType.SESSNSSENDER : sesSnsSenderType.DYNAMOSNSSENDER;
        const snsSender = getSender(senderType);

        await snsSender.send({ event: identifyRequest });
    }
}

module.exports = IdentifierService;