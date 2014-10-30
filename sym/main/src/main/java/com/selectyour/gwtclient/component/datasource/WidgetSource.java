package com.selectyour.gwtclient.component.datasource;// MaiseyenkaDP gdfan 19.08.12 11:16

import com.google.gwt.user.client.ui.Widget;

/**
 * data source for table
 */
public interface WidgetSource {
    /**
     * moves to first element
     */
    void first();

    /**
     * returns next widget or null
     *
     * @return
     */
    Widget getNext();
}
