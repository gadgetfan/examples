package com.sprotect.model.entity.baseentity;

/**
 * implementation of base service
 */
public abstract class BaseServiceImpl<E extends BaseEntity, R extends BaseRepository<E>> implements BaseService<E, R> {
    /**
     * path to entities' images
     */
    private static final String PATH_TO_ADDITIONAL_SOURCES = "/db";

    protected R repo;

    protected void setRepo(R repo) {
        this.repo = repo;
    }

    public E findOne(Long id) {
        return repo.findOne(id);
    }

    public E save(E entity) {
        return repo.save(entity);
    }
}
