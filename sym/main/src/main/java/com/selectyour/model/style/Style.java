package com.selectyour.model.style;// MaiseyenkaDP gdfan 17.06.12 18:48

import com.selectyour.model.namedentity.NamedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * style of a picture
 */
@Entity
public class Style extends NamedEntity {
    @Column(nullable = false)
    private String description;

    public String getDescription() {
        return description;
    }
}
