const { identifyText } = require('../identifier');

const { snsSenders } = require('../sender/sns/sender-factory');

const process = async ({ identifyRequest }) => {

    const identifiedResult = await identifyText({ textContent: identifyRequest.comment });

    const snsSender = snsSenders[identifiedResult];

    return await snsSender({ event: identifyRequest });
}

module.exports = {
    process,
}