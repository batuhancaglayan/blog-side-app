package com.assignment.comment.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assignment.comment.app.infra.aws.dynamo.DefaultDynamoClient;
import com.assignment.comment.app.infra.aws.dynamo.DynamoClient;
import com.assignment.comment.app.infra.aws.sqs.DefaultSqsClient;
import com.assignment.comment.app.infra.aws.sqs.SqsClient;

import lombok.Getter;

@Configuration
public class AwsConfig {

	@Bean
	public DynamoClient amazonDynamoDB() {
		return DefaultDynamoClient.builder().build();
	}

	@Bean
	public SqsClient sqsClient() {
		return DefaultSqsClient.builder().build();
	}
}
