package com.selectyour.gwtclient.component.iframe;// MaiseyenkaDP gdfan 22.07.12 10:34

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Frame;
import com.selectyour.gwtclient.base.ResourceUrlUtils;
import com.selectyour.gwtclient.model.ProductImageService;

/**
 * frame with site from other domain
 */
public class IFrame {
    /**
     * id of frame element where for site from other domains
     */
    static private final String SITE_IFRAME_ELEMENT_ID = "sy-site-iframe";

    /**
     * Attribute of iframe to point to initial src
     */
    static private final String IFRAME_SRC_ATTRIBUTE = "sysrc";

    private final Frame frame;
    private DisableDiv disableDiv;
    /**
     * current OUTER url for iframe
     */
    private String currentUrl;

    public IFrame(final ProductImageService productImageService) {
        final Element iframeElement = Document.get().getElementById(SITE_IFRAME_ELEMENT_ID);
        if (iframeElement != null) {
            this.frame = Frame.wrap(iframeElement);
            this.disableDiv = new DisableDiv(frame.getElement());
            disableDiv.setEnabled(false);
            frame.addLoadHandler(new IFrameLoadHandler(productImageService, this));
            reload(iframeElement.getAttribute(IFRAME_SRC_ATTRIBUTE));
        } else {
            this.frame = null;
        }
    }

    /**
     * obtains resource from url and reloads iframe
     *
     * @param url
     */
    public void reload(String url) {
        if (frameExists()) {
            currentUrl = url;
            startReload();
            frame.setUrl(ResourceUrlUtils.getUrlForResource(url));
            //TODO: сделать disabler в виде элемента, создаваемого в компоненте IFrame
        }
    }

    private boolean frameExists() {
        return (frame != null);
    }

    public Frame getFrame() {
        return frame;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    private void startReload() {
        disableDiv.setEnabled(false);
    }

    void finishReload() {
        disableDiv.setEnabled(true);
    }

    public native String getProductData() /*-{
        var id = @com.selectyour.gwtclient.component.iframe.IFrame::SITE_IFRAME_ELEMENT_ID;
        if ($doc.getElementById(id).contentWindow.Sy) {
            return $doc.getElementById(id).contentWindow.Sy.getData();
        } else {
            return '';
        }
    }-*/;
}
