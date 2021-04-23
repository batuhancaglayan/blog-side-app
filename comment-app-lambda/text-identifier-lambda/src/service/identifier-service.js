const { identifyText } = require('../identifier');

const { snsSenders } = require('../sender/sns/sender-factory');

const { logger } = require('../helper/logger');

const process = async ({ identifyRequest }) => {

    const identifiedResult = await identifyText({ textContent: identifyRequest.comment });

    logger.log({level: 'debug', message: `Comment with id: ${identifyRequest.id} identifier as ${identifiedResult}.`});

    const snsSender = snsSenders[identifiedResult];

    return await snsSender({ event: identifyRequest });
}

module.exports = {
    process,
}