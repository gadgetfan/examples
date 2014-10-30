package com.selectyour.internalization;

import org.apache.tapestry5.ioc.Messages;

/**
 * Info about page
 */
public class PageInfo {
    /**
     * property in app.properties file with translated page title
     */
    private static final String I18N_TITLE_PROPERTY = ".menu_title";

    /**
     * page class name
     */
    private String pageName;

    /**
     * page title
     */
    private String menuTitle;

    public PageInfo(String pageClassName, Messages messages) {
        this.pageName = pageClassName;
        this.menuTitle = messages.get(pageClassName.toLowerCase() + I18N_TITLE_PROPERTY);
    }

    public String getPageName() {
        return pageName;
    }

    public String getMenuTitle() {
        return menuTitle;
    }
}
