package com.assignment.text.identifier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assignment.text.identifier.infra.aws.dynamo.DefaultDynamoClient;
import com.assignment.text.identifier.infra.aws.dynamo.DynamoClient;
import com.assignment.text.identifier.infra.aws.sqs.DefaultSqsClient;
import com.assignment.text.identifier.infra.aws.sqs.SqsClient;

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
