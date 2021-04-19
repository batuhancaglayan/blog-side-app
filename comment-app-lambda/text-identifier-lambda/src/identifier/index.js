const { getIdentificationRules } = require('../dao/identification-rules-dao')

const { IDENTIFIED_RESULTS } = require('../constant');

const identifyText =  async ({ textContent }) => {

    const illegalWords = await getIdentificationRules();
    const wordArr = textContent.split(" ");

    let find = false;
    for (let index = 0; index < wordArr.length; index++) {
        const word = wordArr[index];
        if (illegalWords[word]) {
            find = true;
            break;
        }
    }

    return find ? IDENTIFIED_RESULTS.DENIEDCOMMENT : IDENTIFIED_RESULTS.ALLOWEDCOMMENT;
}

module.exports = {
    identifyText,
}