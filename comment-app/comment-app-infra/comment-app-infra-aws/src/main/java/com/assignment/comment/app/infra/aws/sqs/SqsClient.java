package com.assignment.comment.app.infra.aws.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SqsClient {

	public <T> void send(String queueUrl, T content) throws JsonProcessingException;

	public <T> void send(String queueUrl, T content, String messageGroupId) throws JsonProcessingException;

	public <T> void send(String queueUrl, T content, String messageGroupId, String messageDeduplicationId)
			throws JsonProcessingException;
}
