package com.assignment.text.identifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.assignment.text.identifier.infra.aws.sqs.SqsClient;

@SpringBootApplication
public class TextIdentifierCommandService {
	
	public static void main(String[] args) {
		SpringApplication.run(TextIdentifierCommandService.class, args);
	}
}
