package com.selectyour.gwtclient;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.selectyour.gwtclient.base.ElementWrapper;
import com.selectyour.gwtclient.base.ResourceUrlUtils;
import com.selectyour.gwtclient.component.canvas.*;
import com.selectyour.gwtclient.component.iframe.DisableDiv;
import com.selectyour.gwtclient.component.iframe.IFrame;
import com.selectyour.gwtclient.component.orderform.OrderForm;
import com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader.ImagePreloader;
import com.selectyour.gwtclient.model.*;
import com.selectyour.gwtclient.transformation.GWTBufferedImage;
import com.selectyour.gwtclient.transformation.services.ImageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SelectYourModule implements EntryPoint, ProductImageService, AfterFirstCCImageLoadHandler, ShowProduct {
    /**
     * id of div element where user photo should be
     */
    static private final String CANVAS_COMPONENT_ELEMENT_ID = "sy-view-canvas";
    /**
     * attribute to show enablity of canvas component
     */
    static private final String CANVAS_COMPONENT_ENABLED_ATTRIBUTE = "syenabled";
    /**
     * attribute for window frame data
     */
    static private final String WINDOW_FRAME_DATA_ATTRIBUTE = "sywframedata";
    /**
     * attribute to element with USER_PICTURE_ELEMENT_ID to point to source of client's photo
     */
    static private final String CLIENT_PHOTO_SOURCE_ATTRIBUTE = "sysrc";
    /**
     * attribute to element with USER_PICTURE_ELEMENT_ID to point to source of product image, to display after component loading
     */
    static private final String PRODUCT_SOURCE_ATTRIBUTE = "syproductsrc";
    /**
     * id of submit input on order form
     */
    static private final String ORDER_SUBMIT_ELEMENT_ID = "sy-order-submit";
    /**
     * id of hidden input to store productId on order form
     */
    static private final String ORDERED_PRODUCT_INPUT_ELEMENT_ID = "sy-order-product-id";

    /**
     * id of element to enable after first image of product insert on client's photo
     */
    static private final String ENABLE_AFTER_PRODUCT_DRAW_ELEMENT_ID = "sy-enable-after-product-draw";

    /* Exported data*/
    /**
     * id of <input type="hidden"></input> with string data about product
     */
    static private final String PRODUCT_DATA_INPUT_ID = "sy-product-data-input";
    /**
     * id of <input type="hidden"></input> with window frame data
     */
    static private final String WINDOW_FRAME_DATA_INPUT_ID = "sy-wframe-input";
    /**
     * id of <input type="hidden"></input> with Base64 data of resulted image (client's photo + product)
     */
    static private final String RESULT_IMAGE_DATA_INPUT_ID = "sy-result-image-data-input";
    /**
     * width of resulted image. Attribute of element with RESULT_IMAGE_DATA_INPUT_ID
     */
    static private final String RESULT_IMAGE_WIDTH_ATTRIBUTE = "syresultimagewidth";
    private final Canvas canvas;
    private final WindowFrameList frameList;
    protected Map<String, Product> productElementMap;
    protected RootPanel canvasRootPanel;
    private DisableDiv disableDiv;
    private InputElement windowFrameDataElement;
    private InputElement productDataElement;
    private InputElement resultImageDataElement;
    private AbstractStretchedComponent clientPhoto;
    private List<ICanvasComponent> distortProductList;
    private OrderForm orderForm;
    private Element enableAfterProductDraw;
    private IFrame siteIFrame;

    public SelectYourModule() {
        productElementMap = new HashMap<String, Product>();
        canvas = Canvas.createIfSupported();
        if (canvas != null) {
            frameList = new WindowFrameList(canvas);
            distortProductList = new ArrayList<ICanvasComponent>();
        } else {
            frameList = null;
            distortProductList = null;
        }
    }

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        this.canvasRootPanel = RootPanel.get(CANVAS_COMPONENT_ELEMENT_ID);

        createCanvasPicture();
        processExternalElements();
        createCanvasPictureEvents();

        setupProductElementMap();

        setupOrderForm();

        siteIFrame = new IFrame(this);

    }

    private void showProductFromParams() {
        String productSrc = canvasRootPanel.getElement().getAttribute(PRODUCT_SOURCE_ATTRIBUTE);
        if (productSrc != null && !productSrc.isEmpty()) {
            showProduct(productSrc, null);
        } else {
            clientPhoto.draw();
            frameList.draw();
        }
    }

    private void processExternalElements() {
        Element wfde = Document.get().getElementById(WINDOW_FRAME_DATA_INPUT_ID);
        if (wfde != null) {
            windowFrameDataElement = InputElement.as(wfde);
        }

        Element pde = Document.get().getElementById(PRODUCT_DATA_INPUT_ID);
        if (pde != null) {
            productDataElement = InputElement.as(pde);
        }

        Element ride = Document.get().getElementById(RESULT_IMAGE_DATA_INPUT_ID);
        if (ride != null) {
            resultImageDataElement = InputElement.as(ride);
        }

        enableAfterProductDraw = Document.get().getElementById(ENABLE_AFTER_PRODUCT_DRAW_ELEMENT_ID);
    }

    private void createCanvasPicture() {
        if (canvas != null) {
            canvas.setCoordinateSpaceWidth(canvasRootPanel.getElement().getClientWidth());
            canvas.setCoordinateSpaceHeight(canvasRootPanel.getElement().getClientHeight());

            //load image into canvas
            String src = "";
            if (canvasRootPanel.getElement().hasAttribute(CLIENT_PHOTO_SOURCE_ATTRIBUTE)) {
                src = canvasRootPanel.getElement().getAttribute(CLIENT_PHOTO_SOURCE_ATTRIBUTE);
            }
            if (!src.isEmpty()) {
                clientPhoto = new CCImage(canvas, src, this);
            }
            canvasRootPanel.add(canvas);

            this.disableDiv = new DisableDiv(canvasRootPanel.getElement());
            disableDiv.setEnabled(Boolean.valueOf(canvasRootPanel.getElement().getAttribute(CANVAS_COMPONENT_ENABLED_ATTRIBUTE)));
        } else {
            VerticalPanel getBrowserPanel = new VerticalPanel();
            getBrowserPanel.getElement().addClassName("browser-incompatible");
            //TODO: internationalize it
            getBrowserPanel.add(new Label("Ваш броузер не поддерживает стандарт html5. Для правильного отображения содержимого сайта, "
                    + "пожалуйста, скачайте один из броузеров по ссылкам указанным ниже:"));
            getBrowserPanel.add(new Anchor("Google Chrome (рекомендуется)", "https://www.google.com/intl/ru/chrome/browser/"));
            getBrowserPanel.add(new Anchor("Firefox", "http://www.mozilla.org/ru/firefox/new/"));
            //verticalPanel.add(new Anchor("Safari", ""));
            getBrowserPanel.add(new Anchor("Internet Explorer 9 (только для Windows 7)", "http://www.microsoft.com/en-us/download/details.aspx?id=13950"));
            canvasRootPanel.add(getBrowserPanel);
        }
    }

    public void process(CCImage clientPhoto) {
        readWFrameData();
        showProductFromParams();
        disableDiv.setEnabled(true);
    }

    private void createCanvasPictureEvents() {
        if (canvas != null) {
            canvas.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    if (event.getNativeButton() == NativeEvent.BUTTON_LEFT) {
                        frameList.addPoint(event.getX(), event.getY());
                    } else {
                        frameList.removeLastPoint();
                    }
                    if (frameList.isCorrect()) {
                        writeWFrameData();
                    }
                    drawAll();
                }
            });
        }
    }

    /**
     * reads window frame data from WINDOW_FRAME_DATA_ATTRIBUTE
     */
    private void readWFrameData() {
        String wFrameData = canvasRootPanel.getElement().getAttribute(WINDOW_FRAME_DATA_ATTRIBUTE);
        if (!wFrameData.isEmpty()) {
            frameList.setDataString(wFrameData, clientPhoto.getImageRectangle(), 1, 1);
        }
    }

    /**
     * writes window frame data to WINDOW_FRAME_DATA_ATTRIBUTE and WINDOW_FRAME_DATA_INPUT_ID
     */
    private void writeWFrameData() {
        String wFrameData = frameList.getDataString(clientPhoto.getImageRectangle());
        if (canvasRootPanel.getElement().hasAttribute(WINDOW_FRAME_DATA_ATTRIBUTE)) {
            canvasRootPanel.getElement().setAttribute(WINDOW_FRAME_DATA_ATTRIBUTE, wFrameData);
        }
        if (windowFrameDataElement != null) {
            windowFrameDataElement.setValue(wFrameData);
        }
    }

    /**
     * Fills productElementMap
     */
    private void setupProductElementMap() {
        NodeList<Element> imgList = Document.get().getElementsByTagName("img");
        addProductsByImages(imgList, false);

        //products from frame loads siteIFrame by oneself
    }

    //TODO: extract in ProductService all tasks, connected with product
    public void addProductsByImages(NodeList<Element> imgList, boolean addPossibleProducts) {
        //Window.alert("imgList.length = " + imgList.getLength());
        final ProductImageClickHandler clickHandler = new ProductImageClickHandler(this);
        final ProductImageLoadHandler loadHandler = new ProductImageLoadHandler(this);

        for (int i = imgList.getLength() - 1; i >= 0; --i) {
            Element img = imgList.getItem(i);
            if ((addPossibleProducts && ProductImageDeterminationService.possibleProductImage(img))
                    || (!addPossibleProducts && ProductImageDeterminationService.isProductImage(img))) {
                Product product = new Product(img);
                final String id = product.getId();
                productElementMap.put(id, product);
                ElementWrapper image = new ElementWrapper(img);
                image.addClickHandler(clickHandler);
                if (img.hasAttribute(Product.PRODUCT_IMAGE_LOAD_ATTRIBUTE)) {
                    ImageElement imageElement = ImageElement.as(image.getElement());
                    String src = imageElement.getSrc();
                    imageElement.setSrc("");
                    image.addLoadHandler(loadHandler);
                    imageElement.setSrc(src);

                    //TODO: for optimization, if needed call event only, without image reloadin
                    //NativeEvent loadEvent = Document.get().createLoadEvent();
                    //DomEvent.fireNativeEvent(loadEvent, image, image.getElement());
                }
            }
        }
    }

    /**
     * Shows on user's photo new image. id may be null
     *
     * @param imageUrl  url of image to show
     * @param productId id of product
     */
    public void showProduct(String imageUrl, final String productId) {
        if (frameList.isFirstFrameFull()) {
            //disableDiv.setEnabled(false);

            saveProductData();
            //remove incorrect last frame, if needed
            if (!frameList.isCorrect()) {
                frameList.removeFrame();
            }

            //with GWTBufferedImage
            ImagePreloader.load(ResourceUrlUtils.getUrlFromParam(imageUrl), new ImageLoadHandler() { //TODO: !for Volhovec only, use getUrlForResource() for other cases
                public void imageLoaded(ImageLoadEvent event) {
                    final GWTBufferedImage srcImage = ImageUtils.imageToGWTBufferedImage(event.takeImage());

                    distortProductList.clear();
                    for (WindowFrame frame : frameList.getFrameList()) {
                        GWTBufferedImage distortedImage = ImageUtils.distortProductToFrame(
                                srcImage, frame);
                        distortProductList.add(new CCCanvas(canvas, distortedImage.getImageData(),
                                frame.getMinX(),
                                frame.getMinY(),
                                false));
                    }
                    if (productId != null && !productId.isEmpty()) {
                        Product product = productElementMap.get(productId);
                        if (product != null) {
                            orderForm.enableSubmit();
                            orderForm.saveProduct(product);
                        }
                    }
                    drawAll();
                    saveResultImageData();
                    //disableDiv.setEnabled(true);

                    if (enableAfterProductDraw != null) {
                        enableAfterProductDraw.removeAttribute("disabled");
                    }
                }
            });
        }
    }

    private void saveProductData() {
        if (productDataElement != null) {
            productDataElement.setValue(siteIFrame.getProductData());
        }
    }

    private void saveResultImageData() {
        if (resultImageDataElement != null) {
            int width = Integer.valueOf(resultImageDataElement.getAttribute(RESULT_IMAGE_WIDTH_ATTRIBUTE));
            Canvas tmpCanvas = ImageUtils.scaleCanvasToWidth(canvas, clientPhoto.getImageRectangle(), width);
            String data = tmpCanvas.toDataUrl("image/jpeg");
            //remove unused non base64 data
            //data.replaceAll("^data:image\\/(png|jpg);base64,", "");
            int firstData = data.indexOf(',') + 1;
            data = data.substring(firstData);
            resultImageDataElement.setValue(data);
        }
    }

    private void drawAll() {
        clientPhoto.draw();

        if (distortProductList.size() == 0) {
            frameList.draw();
        } else {
            for (ICanvasComponent cc : distortProductList) {
                cc.draw();
            }
        }
    }

    private void setupOrderForm() {
        Element idSubmit = Document.get().getElementById(ORDER_SUBMIT_ELEMENT_ID);
        Element idInput = Document.get().getElementById(ORDERED_PRODUCT_INPUT_ELEMENT_ID);
        orderForm = new OrderForm(idSubmit, idInput);
    }

    public void clearProducts() {
        productElementMap.clear();
    }
}
