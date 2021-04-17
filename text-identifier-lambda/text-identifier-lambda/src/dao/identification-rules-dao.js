const { mockKeywords } = require('../mock');

const getIdentificationRules = async () => {
    return mockKeywords;
}

module.exports = {
    getIdentificationRules,
}