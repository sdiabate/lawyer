package com.ngosdi.lawyer.services;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ngosdi.lawyer.beans.AbstractEntity;
import com.ngosdi.lawyer.repositories.IBaseRepository;

@Service
//@org.springframework.context.annotation.Profile("prod")
@SuppressWarnings("unchecked")
public class ServiceDao implements IServiceDao {

	@Autowired
	private ApplicationContext appContext;

	@SuppressWarnings("rawtypes")
	private <T extends AbstractEntity> Optional<IBaseRepository> getBean(final Class<T> clazz) {
		for (final Entry<String, IBaseRepository> entry : appContext.getBeansOfType(IBaseRepository.class).entrySet()) {
			for (final Class<?> interfaze : entry.getValue().getClass().getInterfaces()) {
				for (final Type type : interfaze.getGenericInterfaces()) {
					if (type instanceof ParameterizedType) {
						final Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
						if (genericTypes.length == 2 && genericTypes[0].equals(clazz) && genericTypes[1].equals(Long.class)) {
							return Optional.of(entry.getValue());
						}
					}
				}
			}
		}
		return Optional.empty();
	}

	@SuppressWarnings("rawtypes")
	private <T extends AbstractEntity> IBaseRepository<T, Long> getRepository(final Class<T> clazz) {
		final Optional<IBaseRepository> result = getBean(clazz);
		if (!result.isPresent()) {
			throw new IllegalArgumentException("No repository found for the current element");
		}
		return result.get();
	}

	@Override
	public <T extends AbstractEntity> T save(final T entity) {
		return getRepository((Class<T>) entity.getClass()).saveAndFlush(entity);
	}

	@Override
	public <T extends AbstractEntity> List<T> saveAll(final List<T> entities) {
		final Class<T> entityClass = (Class<T>) entities.get(0).getClass();
		final List<T> result = getRepository(entityClass).save(entities);
		getRepository(entityClass).flush();
		return result;
	}

	@Override
	public <T extends AbstractEntity> List<T> getAll(final Class<T> clazz) {
		return getRepository(clazz).findAll(new Sort(Direction.DESC, "lastModifiedDate"));
	}

	@Override
	public <T extends AbstractEntity> Stream<T> getAllAsStream(final Class<T> clazz) {
		// return getRepository(clazz).getAllAsStream();
		return getAll(clazz).stream();
	}

	@Override
	public <T extends AbstractEntity> List<T> getLatestElements(final Class<T> clazz, final int limit) {
		final Page<T> page = getRepository(clazz).findAll(new PageRequest(0, limit, Direction.DESC, "lastModifiedDate"));
		return page.getContent();
	}

	@Override
	public <T extends AbstractEntity> void delete(final T entity) {
		getRepository((Class<T>) entity.getClass()).delete(entity);
	}

	@Override
	public <T extends AbstractEntity> void delete(final Class<T> clazz, final long id) {
		getRepository(clazz).delete(id);
	}

	@Override
	public <T extends AbstractEntity> void deleteAll(final Class<T> clazz) {
		getRepository(clazz).deleteAll();
	}

	@Override
	public <T extends AbstractEntity> T find(final Class<T> clazz, final long entityId) {
		return getRepository(clazz).findOne(entityId);
	}

}
