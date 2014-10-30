package com.selectyour.components;// MaiseyenkaDP gdfan 08.07.12 16:18

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;

public class StoredLink {
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String caption;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
