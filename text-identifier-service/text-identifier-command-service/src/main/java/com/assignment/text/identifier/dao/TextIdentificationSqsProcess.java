package com.assignment.text.identifier.dao;

import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.assignment.text.identifier.model.TextIdentificationProcessModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TextIdentificationSqsProcess implements TextIdentificationProcess {

	private AmazonSQS amazonSqs;

	private ObjectMapper objectMapper;

	public TextIdentificationSqsProcess(AmazonSQS amazonSqs, ObjectMapper objectMapper) {
		this.amazonSqs = amazonSqs;
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean startProcess(TextIdentificationProcessModel textIdentificationProcessModel) {

		try {
			String content = objectMapper
					.writeValueAsString(textIdentificationProcessModel);
			SendMessageRequest send_msg_request = new SendMessageRequest()
					.withQueueUrl(
							"https://sqs.eu-central-1.amazonaws.com/270045217160/text-identification-request-queue")
					.withMessageBody(content);

			amazonSqs.sendMessage(send_msg_request);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return false;
	}

}
