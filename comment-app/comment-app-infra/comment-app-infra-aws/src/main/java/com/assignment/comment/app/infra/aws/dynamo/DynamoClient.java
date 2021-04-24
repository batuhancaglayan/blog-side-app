package com.assignment.comment.app.infra.aws.dynamo;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.GetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;

public interface DynamoClient {

	public GetItemOutcome getById(String tableName, KeyAttribute... components);
	
	public UpdateItemOutcome updateItem(String table, String updateExpression, Map<String, Object> valueMap,
			KeyAttribute... components);
}
