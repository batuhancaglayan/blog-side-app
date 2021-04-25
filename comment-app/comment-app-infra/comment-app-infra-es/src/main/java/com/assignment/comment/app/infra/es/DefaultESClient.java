package com.assignment.comment.app.infra.es;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class DefaultESClient implements ESClient {

	protected RestHighLevelClient restHighLevelClient;

	public DefaultESClient(RestHighLevelClient restHighLevelClient) {
		this.restHighLevelClient = restHighLevelClient;
	}

	@Override
	public SearchResponse search(String index, QueryBuilder query, int from, int size) throws IOException {

		SearchRequest searchRequest = new SearchRequest(index)
				.source(new SearchSourceBuilder().query(query).from(from).size(size));

		return this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
	}

	// TODO: Write other methods here
}
