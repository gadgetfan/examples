package com.selectyour.components;// MaiseyenkaDP gdfan 30.07.12 22:20

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * canvas part of Select Your component
 */
@Import(stylesheet = "context:basecomponent/basecomponent.css")
public class SyCanvasComponent {
    /**
     * path to main image for canvas. If is "", then no image will be loaded in component.
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.ASSET, required = true)
    private String clientPhoto;

    /**
     * Canvas height, as percent of browser window height
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
    private int windowHeightPercent;

    /**
     * Data with coorfinates of window frames
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String windowFrameData = "";

    /**
     * path to image, to dislay it immediatelly after comonent loading
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String productSrc = "";

    /**
     * is component initally enabled
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String enabled = "true";

}
