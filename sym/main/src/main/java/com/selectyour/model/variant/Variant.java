package com.selectyour.model.variant;

import com.selectyour.model.baseentity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Variant - a variant of probe's client's photo and some product image
 */
@Entity
public class Variant extends BaseEntity {
    @Column(nullable = false)
    private String productImageSrc;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "probe_id")
    private Long probeId;

    public String getProductImageSrc() {
        return productImageSrc;
    }

    public void setProductImageSrc(String productImageSrc) {
        this.productImageSrc = productImageSrc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProbeId() {
        return probeId;
    }

    public void setProbeId(Long probeId) {
        this.probeId = probeId;
    }
}

