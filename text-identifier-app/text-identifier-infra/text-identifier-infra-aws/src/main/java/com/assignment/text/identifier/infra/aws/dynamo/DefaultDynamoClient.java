package com.assignment.text.identifier.infra.aws.dynamo;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;

@Builder(toBuilder = true)
public class DefaultDynamoClient implements DynamoClient {

	@Builder.Default
	private DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().build());

	@Builder.Default
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public UpdateItemOutcome updateItem(String tableName, String updateExpression, Map<String, Object> valueMap,
			KeyAttribute... components) {

		Table table = dynamoDB.getTable(tableName);
		
		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(components)
				.withUpdateExpression(updateExpression).withValueMap(valueMap)
				.withReturnValues(ReturnValue.UPDATED_NEW);
		
		return table.updateItem(updateItemSpec);
	}
}
