package com.assignment.comment.app.infra.es;

import java.io.IOException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;

public interface ESClient {

	public SearchResponse search(String index, QueryBuilder query, int from, int size) throws IOException;
}
