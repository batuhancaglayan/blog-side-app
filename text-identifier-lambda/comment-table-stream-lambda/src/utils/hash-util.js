const md5 = require('md5');

const md5Hash = (content) => {
    return md5(content);
}

module.exports = {
    md5Hash,
}