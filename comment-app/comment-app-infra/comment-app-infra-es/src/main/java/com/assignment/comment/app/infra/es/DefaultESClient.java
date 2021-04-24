package com.assignment.comment.app.infra.es;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.assignment.comment.app.infra.es.exception.ESClientException;
import com.assignment.comment.app.infra.es.exception.ESClientIndexNotFoundException;

public class DefaultESClient implements ESClient {

	protected RestHighLevelClient restHighLevelClient;

	public DefaultESClient(RestHighLevelClient restHighLevelClient) {
		this.restHighLevelClient = restHighLevelClient;
	}

	@Override
	public SearchResponse search(String index, QueryBuilder query, int from, int size) {
		try {
			SearchRequest searchRequest = new SearchRequest(index)
					.source(new SearchSourceBuilder().query(query).from(from).size(size));
			return this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		}
		// TODO: ADD more specific exception types
		catch (ElasticsearchStatusException e) {
			throw new ESClientIndexNotFoundException(e.getMessage(), e);
		} catch (Exception e) {
			throw new ESClientException(e.getMessage(), e);
		}
	}
}
