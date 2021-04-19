/*
const middy = require('@middy/core');
const ssm = require('@middy/ssm');
const pkg = require('../../package');

const { MICROSERVICE } = require('../constants');
*/

const config = async () => ({
  deneme: 'deneme', 
});

/*
const middyConfig = middy(config);

middyConfig.use(ssm({
  cache: true,
  paths: {
    SSM: process.env.SSM_PREFIX,
  },
}));
*/

module.exports.configInit = config;
