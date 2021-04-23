package com.assignment.comment.app.dao;

import java.time.Instant;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.assignment.comment.app.config.DynamoProperties;
import com.assignment.comment.app.infra.aws.dynamo.DynamoClient;
import com.assignment.comment.app.model.data.CommentModel;
import com.assignment.comment.app.process.TextIdentificationProcess;

@Component
public class CommentDaoImpl implements CommentDao {

	private static final String SOFT_DELETE_QUERY = "set isActive = :isActive, updatedAt = :updatedAt";
	
	private DynamoClient dynamoClient;

	private TextIdentificationProcess textIdentificationProcess;
	
	private DynamoProperties dynamoProperties;

	public CommentDaoImpl(DynamoClient dynamoClient, TextIdentificationProcess textIdentificationProcess, DynamoProperties dynamoProperties) {
		this.dynamoClient = dynamoClient;
		this.textIdentificationProcess = textIdentificationProcess;
		this.dynamoProperties = dynamoProperties;
	}

	@Override
	public boolean createComment(CommentModel commentModel) {
		return this.textIdentificationProcess.startProcess(commentModel);
	}

	@Override
	public void softDeleteItem(String commentId) {
		this.dynamoClient.updateItem(this.dynamoProperties.getCommentTableName(), SOFT_DELETE_QUERY,
				new HashMap<String, Object>() {
					{
						put(":isActive", 0);
						put(":updatedAt", Instant.now().getEpochSecond());
					}
				}, new KeyAttribute("id", commentId));
	}
}
