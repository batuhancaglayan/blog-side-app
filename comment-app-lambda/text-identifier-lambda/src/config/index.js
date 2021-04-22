const config = {
  region: process.env.REGION || '',
  verifiedCommentSnsTopic = process.env.VERIFIED_COMMENT_SNS_TOPIC || '',
  bannedCommentSnsTopic = process.env.BANNED_COMMENT_SNS_TOPIC || '',
}

module.exports = {
  config
};