package com.sprotect.model.entity.baseentity;// MaiseyenkaDP gdfan 17.06.12 18:23

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * ancestor of all entities
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }
}
