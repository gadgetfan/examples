package com.sprotect.model.entity.namedentity;

import com.sprotect.model.entity.baseentity.BaseService;

/**
 * interface for NamedEntity service
 */
public interface NamedService<E extends NamedEntity, R extends NamedRepository<E>> extends BaseService<E, R> {
    E findOneByName(String name);
}
