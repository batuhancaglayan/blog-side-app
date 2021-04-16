package com.assignment.text.identifier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

@Configuration
public class AppConfig {

	@Bean
	public AmazonS3 amazonS3() {
		AmazonS3 s3client = AmazonS3ClientBuilder.standard().build();
		return s3client;
	}

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		AmazonDynamoDB dynamoClient = AmazonDynamoDBClientBuilder.standard().build();
		return dynamoClient;
	}

	@Bean
	public AmazonSQS amazonSQS() {	
		AmazonSQS sqsClient = AmazonSQSClientBuilder.standard().build();
		return sqsClient;
	}
}
