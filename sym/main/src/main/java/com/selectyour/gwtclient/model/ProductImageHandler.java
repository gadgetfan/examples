package com.selectyour.gwtclient.model;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.selectyour.gwtclient.ShowProduct;

/**
 * Abstract handler to show image on user's photo
 */
public abstract class ProductImageHandler {
    private final ShowProduct mainModule;


    public ProductImageHandler(ShowProduct mainModule) {
        this.mainModule = mainModule;
    }

    protected void process(Element element) {
        while (!(element.hasTagName("IMG") || element.hasTagName("img"))) {  //html - IMG, xhtml - img
            element = element.getParentElement();
        }

        ImageElement image = ImageElement.as(element);
        String id = image.getAttribute(Product.PRODUCT_IMAGE_ID_ATTRIBUTE);
        mainModule.showProduct(image.getSrc(), id);
    }

}
