console.log('Loading function');

const MockTextIdentifier = require('./identifier/mock-text-identifier');
const MockIdentificationRulesDao = require('./dao/mock-identification-rules-dao');
const IdentifierService = require('./service/identifier-service')

const identifierService = new IdentifierService({
    textIdentifier: new MockTextIdentifier({
        identificationRulesDao: new MockIdentificationRulesDao()
    })
});

exports.handler = async (event) => {

    for (const { messageId, body } of event.Records) {

        console.log('SQS message %s: %j', messageId, body);

        const bodyObj = JSON.parse(body);
        const result = await identifierService.identify({ identifyRequest: bodyObj });

        console.log('result', result);
    }

    return `Successfully processed  messages.`;
};
