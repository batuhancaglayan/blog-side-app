const config = {
  region: process.env.REGION || '', // 'eu-central-1',
  verifiedCommentSnsTopic: process.env.VERIFIED_COMMENT_SNS_TOPIC || '', //'arn:aws:sns:eu-central-1:270045217160:BannedCommentSns',
  bannedCommentSnsTopic: process.env.BANNED_COMMENT_SNS_TOPIC || '', //'arn:aws:sns:eu-central-1:270045217160:VerifiedCommentSns',
}

module.exports = {
  config
};