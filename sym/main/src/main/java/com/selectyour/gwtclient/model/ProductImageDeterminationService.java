package com.selectyour.gwtclient.model;// MaiseyenkaDP gdfan 21.07.12 10:53

import com.google.gwt.dom.client.Element;

public class ProductImageDeterminationService {
    static public boolean isProductImage(Element element) {
        return (element.getTagName().toUpperCase().equals("IMG")
                && (element.hasAttribute(Product.PRODUCT_IMAGE_ID_ATTRIBUTE)
                || element.hasAttribute(Product.PRODUCT_IMAGE_LOAD_ATTRIBUTE)));
    }


    public static boolean possibleProductImage(Element element) {
        boolean result = false;
        if (element.getTagName().toUpperCase().equals("IMG")) {
            /*ImageElement imageElement = ImageElement.as(element);
            result = imageElement.getHeight() > 150 && imageElement.getHeight() < 400
                  && imageElement.getWidth() > 50 && imageElement.getWidth() < 200;*/
            result = true;
        }

        return result;
    }
}
