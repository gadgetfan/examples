package com.selectyour.gwtclient.base;// MaiseyenkaDP gdfan 22.07.12 9:54

import com.google.gwt.http.client.URL;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

/**
 * Encodes URL to get resource from GetResourceServlet
 */
public class ResourceUrlUtils {
    static private final String ROOT = "";
    static private final String URL_PARAM = "?syurl=";
    static private final String RESOURCE_SERVLET_PATTERN = "/resourcer/res" + URL_PARAM; //TODO: use GetResourceServlet.URL_PARAMETER instead of "url"

    static private final String SERVLET_ROOT = ROOT + RESOURCE_SERVLET_PATTERN;
    static private final RegExp supportedLinks = RegExp.compile("" +
            "([.]*volhovec.ru\\/catalog/$)" + "|" +
            "([.]*volhovec.ru\\/catalog\\/modern[.]*)" + "|" +
            "([.]*volhovec.ru\\/catalog\\/classic[.]*)"
            + "");

    static public String getUrlForResource(String resource) {
        return SERVLET_ROOT + URL.encodeQueryString(resource);
    }

    static public String getUrlFromParam(String urlWithParam) {
        int start = urlWithParam.indexOf(URL_PARAM);
        if (start < 0) {
            return getUrlForResource(urlWithParam);
        } else {
            return urlWithParam;
        }
    }

    /**
     * should
     *
     * @param url
     * @return
     */
    static public boolean isSupportedLink(String url) {
        MatchResult matcher = supportedLinks.exec(url);
        return (matcher != null);
    }

}
