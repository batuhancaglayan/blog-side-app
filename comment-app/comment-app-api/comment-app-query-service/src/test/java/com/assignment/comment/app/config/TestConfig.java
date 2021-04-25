package com.assignment.comment.app.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.assignment.comment.app.infra.es.DefaultESClient;
import com.assignment.comment.app.infra.es.ESClient;

@Profile("test")
@Configuration
public class TestConfig {
	
	@Value("${es.test.host}")
	private String esHost;
	
	@Value("${es.test.port}")
	private Integer esPort;

	@Bean
	public RestHighLevelClient restHighLevelClient() {
	    RestHighLevelClient client = new RestHighLevelClient(
	            RestClient.builder(new HttpHost(esHost, esPort, "http")));
	        return client;
	}

	@Bean
	public ESClient eSClient() {
		return new DefaultESClient(restHighLevelClient());
	}
	

}
