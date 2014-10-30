package com.selectyour.gwtclient;

import com.google.gwt.user.client.ui.Label;
import com.selectyour.gwtclient.model.Product;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Module with demo limits
 */
public class SelectYourModuleDemo extends SelectYourModule {
    public SelectYourModuleDemo() {
        super();
        productElementMap = new LinkedHashMap<String, Product>() {
            @Override
            @SuppressWarnings("deprecation")
            protected boolean removeEldestEntry(Map.Entry<String, Product> eldest) {
                /*
                 * Limit number of images to 5
                 */
                int maxCnt = (new Date()).getYear() / 28 + 1; //5 for 2012y //TODO: remove deprecated method
                return size() > maxCnt;
            }
        };
    }

    @Override
    public void onModuleLoad() {
        super.onModuleLoad();

        /*Removes canvasRootPanel*/
        if ((new Date()).getTime() > 1357014601472L) {
            for (int i = 0; i < canvasRootPanel.getWidgetCount(); ++i) {
                canvasRootPanel.remove(i);
            }
        }

        /*
        * Displays label
        */
        canvasRootPanel.insert(new Label("Demo version"), 0);

    }
}
