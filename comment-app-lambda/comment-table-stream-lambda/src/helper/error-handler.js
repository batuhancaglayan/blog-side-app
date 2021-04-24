const { logger } = require('../helper/logger');

const errorHandler = (fn) => {
    return async function (){
      try{
        return await fn.apply(this, arguments);
      }catch(err){
        logger.log({level: 'error', message: err});
        throw err; // re-throw for middy
      }
    };
};

module.exports = {
    errorHandler,
}