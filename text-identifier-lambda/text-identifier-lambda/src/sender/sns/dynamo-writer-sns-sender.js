const SnsSender = require('./sns-sender')

class DynamoWriterSnsSender extends SnsSender {

    constructor({ topic }) {
        super({ 
            topic,
            subject: 'success identifaction'
        })
    }
}

module.exports = DynamoWriterSnsSender;