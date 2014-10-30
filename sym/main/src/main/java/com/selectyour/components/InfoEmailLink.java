package com.selectyour.components;// MaiseyenkaDP gdfan 08.07.12 14:52

import com.selectyour.gwtshared.PictureConstants;

/**
 * link to info email
 */
public class InfoEmailLink extends EmailLink {
    @Override
    public String getEmail() {
        return "info@" + PictureConstants.DOMAIN_NAME;
    }
}
