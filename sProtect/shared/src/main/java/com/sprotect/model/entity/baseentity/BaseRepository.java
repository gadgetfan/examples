package com.sprotect.model.entity.baseentity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * abstract Ancestor of repositories
 */
@Transactional(readOnly = true)
public interface BaseRepository<E extends BaseEntity> extends CrudRepository<E, Long> {
    List<E> findAll(Specification<E> spec);
    List<E> findAll();
}
