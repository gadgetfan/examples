package com.selectyour.model.namedentity;

import com.selectyour.model.baseentity.BaseServiceImpl;

import java.util.List;

/**
 * base implementation of NamedService
 */
public abstract class NamedServiceImpl<E extends NamedEntity, R extends NamedRepository<E>> extends BaseServiceImpl<E, R> implements NamedService<E, R> {
    public E findOneByName(String name) {
        NamedEntitySpecifications<E> namedEntitySpecifications = new NamedEntitySpecifications<E>();
        List<E> resultList = repo.findAll(namedEntitySpecifications.nameEqual(name));
        if (resultList.size() == 1) { //should be unique result
            return resultList.get(0);
        } else {
            return null;
        }
    }
}
