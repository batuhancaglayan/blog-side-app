package com.assignment.comment.app.dao;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.assignment.comment.app.infra.aws.dynamo.AbstractDynamoDao;
import com.assignment.comment.app.model.data.CommentTable;

@Component
public class CommentDao extends AbstractDynamoDao<CommentTable, String> {

	public CommentDao(DynamoDBMapper dynamoDBMapper) {
		super(dynamoDBMapper);
	}
}
