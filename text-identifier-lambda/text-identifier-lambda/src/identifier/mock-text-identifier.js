const TextIdentifier = require('./text-identifier');

class MockTextIdentifier extends TextIdentifier {

    constructor({ identificationRulesDao }) {
        super({ identificationRulesDao })
    }

    async process({ textContent }) {
        const { identificationRulesDao } = this;

        const illegalWords = await identificationRulesDao.getIdentificationRules();
        const wordArr = textContent.split(" ");

        let find = false;
        for (let index = 0; index < wordArr.length; index++) {
            const word = wordArr[index];
            if (illegalWords[word]) {
                find = true;
                break;
            }
        }

        return find;
    }
}

module.exports = MockTextIdentifier;