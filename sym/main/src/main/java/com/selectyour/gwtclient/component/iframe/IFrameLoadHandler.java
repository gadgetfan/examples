package com.selectyour.gwtclient.component.iframe;// MaiseyenkaDP gdfan 22.07.12 11:02

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.selectyour.gwtclient.base.ElementWrapper;
import com.selectyour.gwtclient.model.ProductImageService;

/**
 * event after iframe is loaded
 */
public class IFrameLoadHandler implements LoadHandler {
    private ProductImageService productImageService;
    private IFrame iframe;
    private IFrameElement iframeElement;

    public IFrameLoadHandler(ProductImageService productImageService, IFrame iframe) {
        this.productImageService = productImageService;
        this.iframe = iframe;
        this.iframeElement = IFrameElement.as(iframe.getFrame().getElement());
    }

    public void onLoad(LoadEvent event) {
        processLinks(); //should be first, it doesn't allow to broad the event if image is in <a>-tag. If you want don't use images from links, then put it after processImages();
        processImages();

        iframe.finishReload();
    }

    private void processImages() {
        productImageService.clearProducts();
        NodeList<Element> imgList = iframeElement.getContentDocument().getElementsByTagName("img");
        productImageService.addProductsByImages(imgList, false);
    }

    private void processLinks() {
        NodeList<Element> anchorList = iframeElement.getContentDocument().getElementsByTagName("a");
        AnchorClickHandler anchorClickHandler = new AnchorClickHandler(iframe);
        for (int e = 0; e < anchorList.getLength(); ++e) {
            final ElementWrapper anchor = new ElementWrapper(anchorList.getItem(e));

            //anchor.getElement().setAttribute("onclick", "");
            //anchor.getElement().setPropertyObject("onclick", null);
            /*if (anchor.getElement().getPropertyObject("onclick") != null)
                Window.alert(anchor.getElement().getPropertyObject("onclick").toString());*/

            anchor.addClickHandler(anchorClickHandler);
        }
    }

}
