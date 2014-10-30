package com.selectyour.model.baseentity;

/**
 * Abstract ancestor of all services
 */
//TODO: Do I need services ? Analize, can I use repository only ?
public interface BaseService<E extends BaseEntity, R extends BaseRepository<E>> {
    /**
     * finds entity by id
     *
     * @param id id of entity
     * @return entity
     */
    E findOne(Long id);

    /**
     * saves entity,
     * if id == null, then creates new entity
     * else updates entity
     *
     * @param entity entity
     * @return entity
     */
    E save(E entity);

    String getImagePath(E entity, String postfix);
}
