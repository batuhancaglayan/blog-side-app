class TextIdentifier {

    constructor({ identificationRulesDao }) {
        this.identificationRulesDao = identificationRulesDao;
    }

    async process({ textContent }) {
        return false;
    }
}

module.exports = TextIdentifier;