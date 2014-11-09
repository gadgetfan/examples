package com.sprotect.model.entity.namedentity;// MaiseyenkaDP gdfan 17.06.12 18:48

import com.sprotect.model.entity.baseentity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
