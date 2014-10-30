package com.selectyour.components;// MaiseyenkaDP gdfan 30.07.12 22:20

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * canvas part of Select Your component
 */
@Import(stylesheet = "context:basecomponent/basecomponent.css")
public class DataLoader {
    /**
     * iframe height, as percent of browser window height
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
    private int windowHeightPercent;

}
