const { v4: uuidv4 } = require('uuid');

const generateUuid = () => {
    return uuidv4();
}

module.exports = {
    generateUuid,
}