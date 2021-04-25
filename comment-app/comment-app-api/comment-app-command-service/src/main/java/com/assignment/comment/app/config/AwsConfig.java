package com.assignment.comment.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.assignment.comment.app.infra.aws.sqs.DefaultSqsClient;
import com.assignment.comment.app.infra.aws.sqs.SqsClient;

@Configuration
public class AwsConfig {

	@Bean
	public DynamoDBMapper dynamoDBMapper() {
		return new DynamoDBMapper(AmazonDynamoDBClientBuilder.standard().build());
	}

	@Bean
	public SqsClient sqsClient() {
		return DefaultSqsClient.builder().build();
	}
}
