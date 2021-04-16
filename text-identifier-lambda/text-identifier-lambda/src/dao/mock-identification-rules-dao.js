const { mockKeywords } = require('../mock');

const IdentificationRulesDao = require('./identification-rules-dao')

class MockIdentificationRulesDao extends IdentificationRulesDao {

    async getIdentificationRules(){
        return mockKeywords;
    }
}

module.exports = MockIdentificationRulesDao;