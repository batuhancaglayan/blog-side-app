package com.assignment.comment.app.process;

import org.springframework.stereotype.Component;

import com.assignment.comment.app.infra.aws.sqs.SqsClient;
import com.assignment.comment.app.infra.exception.AssignmentRuntimeException;
import com.assignment.comment.app.model.data.CommentModel;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class TextIdentificationSqsProcess implements TextIdentificationProcess {

	private SqsClient sqsClient;

	public TextIdentificationSqsProcess(SqsClient sqsClient) {
		this.sqsClient = sqsClient;
	}

	@Override
	public boolean startProcess(CommentModel textIdentificationProcessModel) {

		try {
			this.sqsClient.send(
					"https://sqs.eu-central-1.amazonaws.com/270045217160/text-identification-request-queue",
					textIdentificationProcessModel);
			
			return true;
		} catch (JsonProcessingException e) {
			throw new AssignmentRuntimeException(e.getMessage(), e);
		}
	}

}
