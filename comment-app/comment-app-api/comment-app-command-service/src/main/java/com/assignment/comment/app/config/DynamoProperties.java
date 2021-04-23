package com.assignment.comment.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class DynamoProperties {

	@Value("${aws.dynamo.comment-table-name}")
	private String commentTableName;
}
