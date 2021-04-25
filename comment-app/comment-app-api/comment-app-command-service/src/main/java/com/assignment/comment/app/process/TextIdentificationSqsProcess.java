package com.assignment.comment.app.process;

import org.springframework.stereotype.Component;

import com.assignment.comment.app.config.SqsProperties;
import com.assignment.comment.app.exception.TextIdentificationProcessException;
import com.assignment.comment.app.infra.aws.sqs.SqsClient;
import com.assignment.comment.app.model.data.CommentModel;

@Component
public class TextIdentificationSqsProcess implements TextIdentificationProcess {

	private SqsClient sqsClient;

	private SqsProperties sqsProperties;

	public TextIdentificationSqsProcess(SqsClient sqsClient, SqsProperties sqsProperties) {
		this.sqsClient = sqsClient;
		this.sqsProperties = sqsProperties;
	}

	@Override
	public boolean startProcess(CommentModel textIdentificationProcessModel) {

		try {		
			this.sqsClient.send(this.sqsProperties.getCommentProcessSqs(), textIdentificationProcessModel);
			return true;
		} catch (Exception e) {
			throw new TextIdentificationProcessException(e.getMessage(), e);
		}
	}
}
