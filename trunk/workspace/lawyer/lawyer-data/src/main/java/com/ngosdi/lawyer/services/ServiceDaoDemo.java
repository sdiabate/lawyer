package com.ngosdi.lawyer.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.ngosdi.lawyer.beans.AbstractEntity;

@Service
@Profile("demo")
public class ServiceDaoDemo extends ServiceDao {

	@Override
	public <T extends AbstractEntity> T save(final T entity) {
		final int limit = 10;
		if (getAll(entity.getClass()).size() >= limit) {
			throw new IllegalArgumentException(String.format("Demo version: Can't exceed %d %ss", limit, entity.getClass().getSimpleName()));
		}
		return super.save(entity);
	}
}
