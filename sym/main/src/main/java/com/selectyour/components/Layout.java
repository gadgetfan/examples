package com.selectyour.components;

import com.selectyour.internalization.PageInfo;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

/**
 * Layout component for pages of application test.
 */
@Import(stylesheet = "context:layout/layout.css")
public class Layout {
    /**
     * Pages, that should be in main menu
     */
    private final String[] menuPages = new String[]
            {"doors/selectdispatcher", "doors/photomanagement", "contacts"};

    /**
     * If show menu
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String hasMenu = "true";

    /**
     * The page title, for the <title> element and the <h2> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    /**
     * If true, then title will bee shown on page and in head. If false - in head only
     */
    @Property
    @Parameter(value = "true", defaultPrefix = BindingConstants.LITERAL)
    private String showTitle;

    @Inject
    private Messages messages;

    @Property
    private PageInfo pageInfo;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private boolean widePage = false;

    @Inject
    private ComponentResources resources;

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String appVersion;


    public String getCssClassForPageName() {
        return resources.getPageName().equalsIgnoreCase(pageInfo.getPageName())
                ? "current_page_item"
                : null;
    }

    public PageInfo[] getPageInfos() {
        PageInfo[] pageInfos = new PageInfo[menuPages.length];
        for (int i = 0; i < menuPages.length; ++i) {
            pageInfos[i] = new PageInfo(menuPages[i], messages);
        }

        return pageInfos;
    }

    public String getPageClass() {
        return widePage ? "wide-page" : "page";
    }

    public String getBodyClass() {
        return (Boolean.parseBoolean(hasMenu) ? "with_header" : "no_header");
    }
}
