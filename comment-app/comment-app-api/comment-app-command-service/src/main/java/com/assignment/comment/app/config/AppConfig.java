package com.assignment.comment.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assignment.comment.app.infra.aws.dynamo.DefaultDynamoClient;
import com.assignment.comment.app.infra.aws.dynamo.DynamoClient;
import com.assignment.comment.app.infra.aws.sqs.DefaultSqsClient;
import com.assignment.comment.app.infra.aws.sqs.SqsClient;

@Configuration
public class AppConfig {

//	@Bean
//	public AmazonS3 amazonS3() {
//		AmazonS3 s3client = AmazonS3ClientBuilder.standard().build();
//		return s3client;
//	}

	@Bean
	public DynamoClient amazonDynamoDB() {
		return DefaultDynamoClient.builder().build();
	}

	@Bean
	public SqsClient sqsClient() {
		return DefaultSqsClient.builder().build();
	}
}
