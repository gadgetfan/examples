package com.selectyour.gwtclient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.selectyour.gwtclient.base.ElementWrapper;
import com.selectyour.gwtclient.component.iframe.IFrame;
import com.selectyour.gwtclient.model.Product;
import com.selectyour.gwtclient.model.ProductImageDeterminationService;
import com.selectyour.gwtclient.model.ProductImageLoadHandler;
import com.selectyour.gwtclient.model.ProductImageService;
import com.selectyour.gwtclient.rpc.service.DataLoaderService;
import com.selectyour.gwtclient.rpc.service.DataLoaderServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DataLoaderModule implements EntryPoint, ProductImageService, ShowProduct {
    static private final String LOG_TEXT_AREA_ID = "sylog";

    private IFrame siteIFrame;
    private DataLoaderServiceAsync dataLoaderService;
    private TextAreaElement logArea;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        siteIFrame = new IFrame(this);
        logArea = TextAreaElement.as(Document.get().getElementById(LOG_TEXT_AREA_ID));
        dataLoaderService = GWT.create(DataLoaderService.class);
        ((ServiceDefTarget) dataLoaderService).setServiceEntryPoint("/syService/dataLoader");

    }

    //TODO: extract in ProductService all tasks, connected with product
    public void addProductsByImages(NodeList<Element> imgList, boolean addPossibleProducts) {
        final ProductImageLoadHandler loadHandler = new ProductImageLoadHandler(this);

        for (int i = imgList.getLength() - 1; i >= 0; --i) {
            Element img = imgList.getItem(i);
            if ((addPossibleProducts && ProductImageDeterminationService.possibleProductImage(img))
                    || (!addPossibleProducts && ProductImageDeterminationService.isProductImage(img))) {
                ElementWrapper image = new ElementWrapper(img);
                if (img.hasAttribute(Product.PRODUCT_IMAGE_LOAD_ATTRIBUTE)) {
                    ImageElement imageElement = ImageElement.as(image.getElement());
                    String src = imageElement.getSrc();
                    imageElement.setSrc("");
                    image.addLoadHandler(loadHandler);
                    imageElement.setSrc(src);
                }
            }
        }
    }

    /**
     * Saves data from Page to DB
     *
     * @param imageUrl  url of image to show
     * @param productId id of product
     */
    public void showProduct(String imageUrl, final String productId) {
        dataLoaderService.loadDataVolhovec(siteIFrame.getProductData(), new AsyncCallback<Void>() {
            public void onSuccess(Void result) {
                addSuccessMessage();
            }

            public void onFailure(Throwable caught) {
                addErrorMessage("Error: " + caught.toString());
            }
        });
    }

    private void addErrorMessage(String message) {
        logArea.setValue(logArea.getValue() + "\n" + message);
        Window.alert(message);
    }

    private void addSuccessMessage() {
        logArea.setValue("+" + logArea.getValue());
    }

    public void clearProducts() {
    }
}
