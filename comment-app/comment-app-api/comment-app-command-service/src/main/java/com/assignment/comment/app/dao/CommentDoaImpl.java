package com.assignment.comment.app.dao;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.assignment.comment.app.infra.aws.dynamo.DynamoClient;
import com.assignment.comment.app.model.CommentModel;
import com.assignment.comment.app.process.TextIdentificationProcess;

@Component
public class CommentDoaImpl implements CommentDoa {

	private DynamoClient dynamoClient;
	
	private TextIdentificationProcess textIdentificationProcess;

	public CommentDoaImpl(DynamoClient dynamoClient, TextIdentificationProcess textIdentificationProcess) {
		this.dynamoClient = dynamoClient;
		this.textIdentificationProcess = textIdentificationProcess;
	}
	
	@Override
	public boolean createComment(CommentModel commentModel) {
		return this.textIdentificationProcess.startProcess(commentModel);
	}
	
	@Override
	public void softDeleteItem(String commentId) {
		this.dynamoClient.updateItem("comment-table1", "set isActive = :isActive",
				Collections.singletonMap(":isActive", 0), new KeyAttribute("id", commentId));
	}
}
