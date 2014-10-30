package com.selectyour.gwtclient.model;// MaiseyenkaDP gdfan 21.07.12 12:39

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.selectyour.gwtclient.SelectYourModule;

/**
 * Handles click on product's image
 */
public class ProductImageClickHandler extends ProductImageHandler implements ClickHandler {
    public ProductImageClickHandler(SelectYourModule mainModule) {
        super(mainModule);
    }

    public void onClick(ClickEvent event) {
        Element element = Element.as(event.getNativeEvent().getEventTarget());
        process(element);
    }
}
