const { createLogger, format, transports } = require('winston');

const { config } = require('../config');

const enumerateErrorFormat = format(info => {
    if (info.message instanceof Error) {
      info.message = Object.assign({
        message: info.message.message,
        stack: info.message.stack
      }, info.message);
    }
  
    if (info instanceof Error) {
      return Object.assign({
        message: info.message,
        stack: info.stack
      }, info);
    }
  
    return info;
});

const logger = createLogger({
    format: format.combine(
      enumerateErrorFormat(),
      format.json()
    ),
    transports: [
      new transports.Console()
    ],
    level: config.logLevel,
});

module.exports = {
    logger,
};