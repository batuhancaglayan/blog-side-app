package com.assignment.comment.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class SqsProperties {

	@Value("${aws.sqs.comment-process-sqs}")
	private String commentProcessSqs;
}
