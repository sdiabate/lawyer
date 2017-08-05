package com.ngosdi.lawyer.services;

import java.util.List;
import java.util.stream.Stream;

import com.ngosdi.lawyer.beans.AbstractEntity;

public interface IServiceDao {

	<T extends AbstractEntity> T save(T entity);

	<T extends AbstractEntity> List<T> saveAll(List<T> entities);

	<T extends AbstractEntity> List<T> getAll(Class<T> clazz);

	<T extends AbstractEntity> Stream<T> getAllAsStream(Class<T> clazz);

	<T extends AbstractEntity> List<T> getLatestElements(Class<T> clazz, int limit);

	<T extends AbstractEntity> void delete(T entity);

	<T extends AbstractEntity> void delete(Class<T> clazz, long id);

	<T extends AbstractEntity> void deleteAll(Class<T> clazz);

	<T extends AbstractEntity> T find(Class<T> clazz, long entityId);

}