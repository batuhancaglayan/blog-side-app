package com.assignment.comment.app.process;

import org.springframework.stereotype.Component;

import com.assignment.comment.app.config.SqsProperties;
import com.assignment.comment.app.infra.aws.sqs.SqsClient;
import com.assignment.comment.app.model.data.CommentModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			log.warn("Text identification procees did not start. Process id: " + textIdentificationProcessModel.getId(), e);
			return false;
		}
	}
}
