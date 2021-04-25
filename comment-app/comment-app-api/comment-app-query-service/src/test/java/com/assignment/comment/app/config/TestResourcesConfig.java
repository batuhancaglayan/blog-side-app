package com.assignment.comment.app.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;

@ActiveProfiles("test")
@Configuration
public class TestResourcesConfig {

	// Keyword Search Valid Request Body
	@Value("${keyword.search.valid.endpoint.request}")
	private String keywordSearchValidEndpointRequest;

	// Keyword Search Valid Response Body
	@Value("classpath:keywordSearchValidResponse.json")
	private Resource keywordSearchValidResponse;

	// Keyword Search Invalid Index Request Body
	@Value("${email.search.valid.endpoint.request}")
	private String emailSearchValidEndpointRequest;

	// Keyword Search Invalid Index Response Body
	@Value("classpath:emailSearchValidResponse.json")
	private Resource emailSearchValidEndpointResponse;

	// Keyword Search No Match Request Body
	@Value("${keyword.search.nomatch.endpoint.request}")
	private String keywordSearchNoMatchEndpointRequest;

	// Keyword Search No Match Response Body
	@Value("classpath:keywordSearchNoMatchResponse.json")
	private Resource keywordSearchNoMatchEndpointResponse;
	
	
	


	public static String asString(Resource resource) {
		try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public String getKeywordSearchValidEndpointRequest() {
		return keywordSearchValidEndpointRequest;
	}

	public void setKeywordSearchValidEndpointRequest(String keywordSearchValidEndpointRequest) {
		this.keywordSearchValidEndpointRequest = keywordSearchValidEndpointRequest;
	}

	public String getKeywordSearchValidResponse() {
		return asString(keywordSearchValidResponse);
	}

	public void setKeywordSearchValidResponse(Resource keywordSearchValidResponse) {
		this.keywordSearchValidResponse = keywordSearchValidResponse;
	}

	public String getKeywordSearchNoMatchEndpointRequest() {
		return keywordSearchNoMatchEndpointRequest;
	}

	public void setKeywordSearchNoMatchEndpointRequest(String keywordSearchNoMatchEndpointRequest) {
		this.keywordSearchNoMatchEndpointRequest = keywordSearchNoMatchEndpointRequest;
	}

	public String getKeywordSearchNoMatchEndpointResponse() {
		return asString(keywordSearchNoMatchEndpointResponse);
	}

	public void setKeywordSearchNoMatchEndpointResponse(Resource keywordSearchNoMatchEndpointResponse) {
		this.keywordSearchNoMatchEndpointResponse = keywordSearchNoMatchEndpointResponse;
	}

	public String getEmailSearchValidEndpointRequest() {
		return emailSearchValidEndpointRequest;
	}

	public void setEmailSearchValidEndpointRequest(String emailSearchValidEndpointRequest) {
		this.emailSearchValidEndpointRequest = emailSearchValidEndpointRequest;
	}

	public String getEmailSearchValidEndpointResponse() {
		return asString(emailSearchValidEndpointResponse);
	}

	public void setEmailSearchValidEndpointResponse(Resource emailSearchValidEndpointResponse) {
		this.emailSearchValidEndpointResponse = emailSearchValidEndpointResponse;
	}

	

}
