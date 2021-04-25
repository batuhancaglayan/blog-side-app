package com.assignment.comment.app.model.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;

@Data
@DynamoDBTable(tableName = "comment-table")
public class CommentTable {

	@DynamoDBHashKey
	private String id;

	@DynamoDBAttribute
	private String email;

	@DynamoDBAttribute
	private String comment;
	
	@DynamoDBAttribute
	private Long createdAt;
	
	@DynamoDBAttribute
	private Long updatedAt;
	
	@DynamoDBAttribute
	private Long indentifiedAt;
	
	@DynamoDBAttribute
	private int isActive;
}
