package com.assignment.comment.app.infra.aws.dynamo;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.GetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.assignment.comment.app.infra.aws.dynamo.exception.DynamoClientException;
import com.assignment.comment.app.infra.aws.dynamo.exception.DynamoItemNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;

@Builder(toBuilder = true)
public class DefaultDynamoClient implements DynamoClient {

	@Builder.Default
	protected DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().build());

	@Builder.Default
	protected ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public GetItemOutcome getById(String tableName, KeyAttribute... components) {

		Table table = dynamoDB.getTable(tableName);
		if (table == null) {
			throw new DynamoClientException("There is no table with name" + tableName);
		}

		return table.getItemOutcome(components);
	}

	@Override
	public UpdateItemOutcome updateItem(String tableName, String updateExpression, Map<String, Object> valueMap,
			KeyAttribute... components) {

		Table table = dynamoDB.getTable(tableName);

		if (table == null) {
			throw new DynamoClientException("There is no table with name" + tableName);
		}

		GetItemOutcome item = this.getById(table, components);
		if (item.getItem() == null) {
			throw new DynamoItemNotFoundException("Item not found.");
		}

		try {
			return table
					.updateItem(new UpdateItemSpec().withPrimaryKey(components).withUpdateExpression(updateExpression)
							.withValueMap(valueMap).withReturnValues(ReturnValue.UPDATED_NEW));
		} catch (Exception e) {
			throw new DynamoClientException(e.getMessage(), e);
		}
	}

	protected GetItemOutcome getById(Table table, KeyAttribute... components) {

		if (table == null) {
			throw new DynamoClientException("Table can not be null.");
		}

		return table.getItemOutcome(components);
	}
}
