package com.assignment.comment.app.infra.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;

@Builder(toBuilder = true)
public class DefaultSqsClient implements SqsClient {

	@Builder.Default
	protected AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().build();

	@Builder.Default
	protected ObjectMapper objectMapper = new ObjectMapper();

	public <T> void send(String queueUrl, T content) throws JsonProcessingException {
		send(queueUrl, content, null);
	}

	public <T> void send(String queueUrl, T content, String messageGroupId) throws JsonProcessingException {
		send(queueUrl, content, messageGroupId, null);
	}

	public <T> void send(String queueUrl, T content, String messageGroupId, String messageDeduplicationId)
			throws JsonProcessingException {

		amazonSQS.sendMessage(new SendMessageRequest().withMessageBody(objectMapper.writeValueAsString(content))
				.withQueueUrl(queueUrl).withMessageGroupId(messageGroupId)
				.withMessageDeduplicationId(messageDeduplicationId));
	}
}
