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
           Data: "This message body contains HTML formatting. It can, for example, contain links like this one: <a class=\"ulink\" href=\"http://docs.aws.amazon.com/ses/latest/DeveloperGuide\" target=\"_blank\">Amazon SES Developer Guide</a>."
          }, 
          Text: {
           Charset: "UTF-8", 
           Data: "This is the message body in text format."
          }
         }, 
         Subject: {
          Charset: "UTF-8", 
          Data: "Test email"
         }
        }, 
        Source: "batuhanaskyour@gmail.com", 
        SourceArn: "arn:aws:ses:eu-central-1:270045217160:identity/batuhanaskyour@gmail.com"
    };
}

module.exports = {
    craeteIdentificationFailMail,
}