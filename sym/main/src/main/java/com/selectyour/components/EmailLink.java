package com.selectyour.components;// MaiseyenkaDP gdfan 08.07.12 15:12

/**
 * email link with mailto
 */
public abstract class EmailLink extends StoredLink {
    public abstract String getEmail();

    @Override
    public String getCaption() {
        return (super.getCaption().isEmpty()) ? getEmail() : super.getCaption();
    }

    public String getLink() {
        return "mailto:" + getEmail();
    }
}
