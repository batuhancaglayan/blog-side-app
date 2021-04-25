package com.assignment.comment.app.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Component;

import com.assignment.comment.app.exception.IndexNotFoundException;
import com.assignment.comment.app.infra.es.ESClient;
import com.assignment.comment.app.infra.exception.AssignmentRuntimeException;
import com.assignment.comment.app.model.data.CommentDocument;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CommentDaoImpl implements CommentDao {

	private ESClient esClient;

	private ObjectMapper objectMapper;

	public CommentDaoImpl(ESClient esClient, ObjectMapper objectMapper) {
		this.esClient = esClient;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<CommentDocument> findByEmail(String index, String email, int from, int size) {

		try {
			List<CommentDocument> result = new ArrayList<>();

			BoolQueryBuilder qeury = new BoolQueryBuilder().must(QueryBuilders.matchQuery("email", email))
					.must(QueryBuilders.matchQuery("isActive", 1));

			SearchResponse searchResponse = this.esClient.search(index, qeury, from, size);
			if (searchResponse.status() == RestStatus.OK) {
				SearchHit[] searchHits = searchResponse.getHits().getHits();

				if (searchHits.length > 0) {
					Arrays.stream(searchHits).forEach(
							hit -> result.add(objectMapper.convertValue(hit.getSourceAsMap(), CommentDocument.class)));
				}
			}

			return result;
		}
		// TODO: ADD more specific exception types
		catch (ElasticsearchStatusException e) {
			throw new IndexNotFoundException(e.getMessage(), e);
		} catch (Exception e) {
			throw new AssignmentRuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<CommentDocument> findByEmailAndSearchInComment(String index, String email, String keyword, int from,
			int size) {

		try {
			List<CommentDocument> result = new ArrayList<>();

			BoolQueryBuilder qeury = new BoolQueryBuilder().must(QueryBuilders.matchQuery("email", email))
					.must(QueryBuilders.matchQuery("isActive", 1)).must(QueryBuilders.matchQuery("comment", keyword));

			SearchResponse searchResponse = this.esClient.search(index, qeury, from, size);
			if (searchResponse.status() == RestStatus.OK) {
				SearchHit[] searchHits = searchResponse.getHits().getHits();

				if (searchHits.length > 0) {
					Arrays.stream(searchHits).forEach(
							hit -> result.add(objectMapper.convertValue(hit.getSourceAsMap(), CommentDocument.class)));
				}
			}

			return result;
		}
		// TODO: ADD more specific exception types
		catch (ElasticsearchStatusException e) {
			throw new IndexNotFoundException(e.getMessage(), e);
		} catch (Exception e) {
			throw new AssignmentRuntimeException(e.getMessage(), e);
		}
	}
}
