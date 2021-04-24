const { config } = require('../../config');

const craeteIdentificationFailMail = async (destination, data) => {
    return  {
        Destination: {
         ToAddresses: [
            `${destination}`
         ]
        }, 
        Message: {
         Body: {
          Html: {
           Charset: "UTF-8", 
           Data: `Your comment: ${message.id} contains banned keyword/s. So that it will not be stored.`
          }, 
          Text: {
           Charset: "UTF-8", 
           Data: `Your comment: ${message.id} contains banned keyword/s. So that it will not be stored.`
          }
         }, 
         Subject: {
          Charset: "UTF-8", 
          Data: "Banned Comment"
         }
        }, 
        Source: config.senderMail, 
        SourceArn: `arn:aws:ses:${config.region}:${config.account}:identity/${config.senderMail}`
    };
}

module.exports = {
    craeteIdentificationFailMail,
}