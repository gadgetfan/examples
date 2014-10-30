package com.selectyour.gwtclient.component.iframe;// MaiseyenkaDP gdfan 22.07.12 20:21

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;

import java.util.Date;

/**
 * div to disable the frame
 * TODO: now should be right after element, that it disables. Need create automatically, not in .tml
 */
public class DisableDiv {
    /**
     * css class to make disabler disabled
     */
    static private final String DISABLE_DISABLER = "sy-disable";
    /**
     * css class to make disabler enabled
     */
    static private final String ENABLE_DISABLER = "sy-enable";

    private Element element;
    /**
     * note the time between disabling and enabling
     */
    private boolean noteTheTime;
    private long startTime;

    public DisableDiv(Element element, boolean noteTheTime) {
        this.element = Element.as(element.getNextSibling());
        this.noteTheTime = noteTheTime;
        //setEnabled(false);
        this.element.setAttribute("style",
                " top:" + element.getAbsoluteTop() + "px;"
                        + " left:" + element.getAbsoluteLeft() + "px;"
                        + " width:" + element.getOffsetWidth() + "px;"
                        + " height:" + element.getOffsetHeight() + "px;");
    }

    public DisableDiv(Element element) {
        this(element, false);
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            element.removeClassName(DISABLE_DISABLER);
            element.addClassName(ENABLE_DISABLER);

            if (noteTheTime) {
                long elapsedTime = (new Date()).getTime() - startTime;
                Window.alert("Elapsed time: " + elapsedTime + " ms.");
            }
        } else {
            element.removeClassName(ENABLE_DISABLER);
            element.addClassName(DISABLE_DISABLER);

            if (noteTheTime) {
                startTime = (new Date()).getTime();
            }
        }
    }


}
