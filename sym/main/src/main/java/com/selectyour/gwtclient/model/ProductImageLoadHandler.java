package com.selectyour.gwtclient.model;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.selectyour.gwtclient.ShowProduct;

/**
 *
 */
public class ProductImageLoadHandler extends ProductImageHandler implements LoadHandler {
    public ProductImageLoadHandler(ShowProduct mainModule) {
        super(mainModule);
    }

    public void onLoad(LoadEvent event) {
        Element element = Element.as(event.getNativeEvent().getEventTarget());
        process(element);
    }
}
