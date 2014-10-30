package com.selectyour.model.baseentity;

import com.selectyour.gwtshared.SharedConstants;

/**
 * implementation of base service
 */
public abstract class BaseServiceImpl<E extends BaseEntity, R extends BaseRepository<E>> implements BaseService<E, R> {
    /**
     * path to entities' images
     */
    private static final String PATH_TO_DB_IMAGES = String.format("/%s/db", SharedConstants.WEB_CONTEXT);

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

    public String getImagePath(E entity, String dir) {
        if (entity.getId() != null) {
            if (dir != null) {
                return String.format("%s/%s/%s/%d.gif", PATH_TO_DB_IMAGES, entity.getClass().getSimpleName().toLowerCase(),
                        dir, entity.getId());
            } else {
                return String.format("%s/%s/%d.gif", PATH_TO_DB_IMAGES, entity.getClass().getSimpleName().toLowerCase(),
                        entity.getId());
            }
        } else {
            return null;
        }
    }

    protected Long cookieToId(String stringId) {
        Long id;
        try {
            id = Long.parseLong(stringId);
        } catch (NumberFormatException e) {
            id = null;
        }
        return id;
    }

    protected String idToCookie(Long id) {
        return id.toString();
    }

}
