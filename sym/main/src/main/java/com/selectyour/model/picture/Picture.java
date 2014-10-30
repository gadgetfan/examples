package com.selectyour.model.picture;// MaiseyenkaDP gdfan 17.06.12 18:48

import com.selectyour.model.namedentity.NamedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Picture extends NamedEntity {
    @Column(nullable = false, name = "style_id")
    private Long styleId;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double height;

    public Long getStyleId() {
        return styleId;
    }

    public Double getPrice() {
        return price;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }
}
