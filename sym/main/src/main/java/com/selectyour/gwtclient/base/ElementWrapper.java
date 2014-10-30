package com.selectyour.gwtclient.base;// MaiseyenkaDP gdfan 21.07.12 10:28

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class ElementWrapper extends Widget implements HasClickHandlers {

    public ElementWrapper(Element element) {
        setElement(element);
        super.onAttach();//magic :) from http://stackoverflow.com/a/1689824
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    public HandlerRegistration addLoadHandler(LoadHandler handler) {
        return addDomHandler(handler, LoadEvent.getType());
    }
}