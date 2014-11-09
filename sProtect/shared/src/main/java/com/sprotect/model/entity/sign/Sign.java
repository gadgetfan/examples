package com.sprotect.model.entity.sign;// MaiseyenkaDP gdfan 17.06.12 18:48

import com.sprotect.model.entity.namedentity.NamedEntity;

import javax.persistence.Entity;

/**
 * sign
 */
@Entity
public class Sign extends NamedEntity {
    public Sign(String name) {
        super();
        this.setName(name);
    }

    public Sign() {
    }
}
