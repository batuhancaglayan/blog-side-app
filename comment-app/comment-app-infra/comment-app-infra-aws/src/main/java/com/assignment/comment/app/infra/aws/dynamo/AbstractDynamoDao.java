package com.assignment.comment.app.infra.aws.dynamo;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public abstract class AbstractDynamoDao<T, ID extends Serializable> {

	protected DynamoDBMapper mapper;

	protected Class<T> entityClass;

	protected AbstractDynamoDao(DynamoDBMapper dynamoDBMapper) {
		this.mapper = dynamoDBMapper;

		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	public void save(T t) {
		mapper.save(t);
	}

	public T findOne(ID id) {
		return mapper.load(entityClass, id);
	}
	
	// TODO: Write other methods here
}