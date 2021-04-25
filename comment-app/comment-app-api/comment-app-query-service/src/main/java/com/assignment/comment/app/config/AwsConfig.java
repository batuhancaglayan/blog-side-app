package com.assignment.comment.app.config;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.assignment.comment.app.infra.aws.common.AWSRequestSigningApacheInterceptor;
import com.assignment.comment.app.infra.es.DefaultESClient;
import com.assignment.comment.app.infra.es.ESClient;

@Configuration
public class AwsConfig {

	private ElasticsearchProperties elasticsearchProperties;
	
	public AwsConfig(ElasticsearchProperties elasticsearchProperties) {
		this.elasticsearchProperties = elasticsearchProperties;
	}

	@Bean
	public AWSCredentialsProvider credentialsProvider() {
		return new DefaultAWSCredentialsProviderChain();
	}

	@Bean
	public RestHighLevelClient restHighLevelClient(AWSCredentialsProvider credentialsProvider) {
		
		AWS4Signer signer = new AWS4Signer();
		signer.setServiceName(this.elasticsearchProperties.getServiceName());
		signer.setRegionName(this.elasticsearchProperties.getRegion());
		
		HttpRequestInterceptor interceptor = new AWSRequestSigningApacheInterceptor(
				this.elasticsearchProperties.getServiceName(), 
				signer,
				credentialsProvider);
		
		return new RestHighLevelClient(
				RestClient.builder(HttpHost.create(this.elasticsearchProperties.getEndpoint()))
				.setHttpClientConfigCallback(hacb -> hacb.addInterceptorLast(interceptor)));
	}

	@Bean
	public ESClient eSClient() {
		return new DefaultESClient(restHighLevelClient(credentialsProvider()));
	}

}
