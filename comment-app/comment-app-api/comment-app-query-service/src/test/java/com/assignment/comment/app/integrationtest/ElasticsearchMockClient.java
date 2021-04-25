package com.assignment.comment.app.integrationtest;

import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import com.assignment.comment.app.config.TestResourcesConfig;

public class ElasticsearchMockClient {

	private final MockServerClient client;

	private TestResourcesConfig testResources;

	public ElasticsearchMockClient(final MockServerClient client, TestResourcesConfig testResources) {
		this.client = client;
		this.testResources = testResources;
		setup();
	}

	public void setup() {

		//Email Search Valid Endpoint Matcher
		this.client.when(HttpRequest.request()
				.withPath(".*/_search.*")
				.withBody(this.testResources.getEmailSearchValidEndpointRequest()))
				.respond(HttpResponse.response(this.testResources.getEmailSearchValidEndpointResponse())
				.withHeader("Content-Type", "application/json"));
		
		//Keyword Search Valid Endpoint Matcher
		this.client.when(HttpRequest.request()
				.withPath(".*/_search.*")
				.withBody(this.testResources.getKeywordSearchValidEndpointRequest()))
				.respond(HttpResponse.response(this.testResources.getKeywordSearchValidResponse())
				.withHeader("Content-Type", "application/json"));

		
		//Keyword Search No Match Endpoint Matcher
		this.client.when(HttpRequest.request()
				.withPath(".*/_search.*")
				.withBody(this.testResources.getKeywordSearchNoMatchEndpointRequest()))
				.respond(HttpResponse.response(this.testResources.getKeywordSearchNoMatchEndpointResponse())
				.withHeader("Content-Type", "application/json"));			
	}
}
