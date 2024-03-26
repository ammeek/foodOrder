package com.example.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import java.util.List;
import java.util.Optional;

public interface IDynamoDBFacadeService {
    <T> Optional<T> load(Class<T> clazz, String primaryKey, String sortKey);
    <T> List<T> list(Class<T> clazz, DynamoDBQueryExpression<T> dynamoDBQueryExpression);
    <T> void save(T object);
}
