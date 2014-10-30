package com.selectyour.gwtclient.model;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.selectyour.gwtclient.utils.UuidUtils;

/**
 * Product entity
 */
public class Product {
    static public final String PRODUCT_IMAGE_LOAD_ATTRIBUTE = "syload";
    static final String PRODUCT_IMAGE_ID_ATTRIBUTE = "syid";
    static private final String PRODUCT_IMAGE_SOURCE_ATTRIBUTE = "sysrc";
    private String id;
    private String imageUrl;

    //TODO: in future get all info about product by id from DB
    public Product(Element element) {
        if (element.hasAttribute(PRODUCT_IMAGE_SOURCE_ATTRIBUTE)) {
            this.imageUrl = element.getAttribute(Product.PRODUCT_IMAGE_SOURCE_ATTRIBUTE);

        } else {
            this.imageUrl = (ImageElement.as(element)).getSrc();
        }

        this.id = element.getAttribute(PRODUCT_IMAGE_ID_ATTRIBUTE);
        if (id == null || id.isEmpty()) {
            this.id = UuidUtils.uuid(8);
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }

}
