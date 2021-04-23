package com.assignment.comment.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ElasticsearchProperties {

	@Value("${aws.es.endpoint}")
	private String endpoint;

	@Value("${aws.es.region}")
	private String region;

	@Value("${aws.es.serviceName}")
	private String serviceName;
}
