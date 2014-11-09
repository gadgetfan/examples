package com.sprotect.model.entity.photomethod;// MaiseyenkaDP gdfan 17.06.12 18:48

import com.sprotect.model.entity.namedentity.NamedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * sign
 */
@Entity
public class PhotoMethod extends NamedEntity {
    @Column(nullable = false)
    private int width;
    @Column(nullable = false)
    private int height;
    @Column(nullable = false)
    private int maxPixelsInSparkleCnt;

    public PhotoMethod(String name) {
        super();
        this.setName(name);
    }

    public PhotoMethod() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxPixelsInSparkleCnt() {
        return maxPixelsInSparkleCnt;
    }
}
