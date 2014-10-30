package com.selectyour.model.namedentity;

import com.selectyour.model.baseentity.BaseService;

/**
 * interface for NamedEntity service
 */
public interface NamedService<E extends NamedEntity, R extends NamedRepository<E>> extends BaseService<E, R> {
    E findOneByName(String name);
}
