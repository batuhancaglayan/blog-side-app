package com.assignment.text.identifier.dao;

import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.assignment.text.identifier.infra.aws.sqs.SqsClient;
import com.assignment.text.identifier.infra.exception.AssignmentRuntimeException;
import com.assignment.text.identifier.model.TextIdentificationProcessModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TextIdentificationSqsProcess implements TextIdentificationProcess {

	private SqsClient sqsClient;

	private ObjectMapper objectMapper;

	public TextIdentificationSqsProcess(SqsClient sqsClient, ObjectMapper objectMapper) {
		this.sqsClient = sqsClient;
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean startProcess(TextIdentificationProcessModel textIdentificationProcessModel) {

		try {
//			String content = objectMapper
//					.writeValueAsString(textIdentificationProcessModel);
//			SendMessageRequest send_msg_request = new SendMessageRequest()
//					.withQueueUrl(
//							"https://sqs.eu-central-1.amazonaws.com/270045217160/text-identification-request-queue")
//					.withMessageBody(content);

			this.sqsClient.send(
					"https://sqs.eu-central-1.amazonaws.com/270045217160/text-identification-request-queue",
					textIdentificationProcessModel);
			
			return true;
		} catch (JsonProcessingException e) {
			throw new AssignmentRuntimeException(e.getMessage(), e);
		}
	}

}
