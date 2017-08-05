package com.ngosdi.lawyer.repositories;

import java.io.Serializable;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.ngosdi.lawyer.beans.AbstractEntity;

@NoRepositoryBean
public interface IBaseRepository<T extends AbstractEntity, ID extends Serializable> extends JpaRepository<T, ID> {

	@Query("select entity from #{#entityName} entity")
	Stream<T> getAllAsStream();
}
