package com.selectyour.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

import java.util.Collections;
import java.util.List;

/**
 * Removes all javascript from tapestry
 */
//TODO: arch in tapestry 5.4 used good new javascript module, but need time to use it with GWT
public class EmptyJavaScriptStack implements JavaScriptStack {
    public List<String> getStacks() {
        return Collections.emptyList();
    }

    public List<Asset> getJavaScriptLibraries() {
        return Collections.emptyList();
    }

    public List<StylesheetLink> getStylesheets() {
        return Collections.emptyList();
    }

    public String getInitialization() {
        return "";
    }
}
