package com.selectyour.gwtclient.component.datacellwidget.processedphoto;// MaiseyenkaDP gdfan 19.08.12 14:40

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.selectyour.gwtclient.component.canvas.*;
import com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader.ImagePreloader;
import com.selectyour.gwtclient.rpc.dto.PictureDto;
import com.selectyour.gwtclient.transformation.GWTBufferedImage;
import com.selectyour.gwtclient.transformation.services.ImageUtils;

/**
 * inserts productImage on client's photo
 */
class ProcessPhotoHandler implements AfterFirstCCImageLoadHandler {
    /**
     * id of <input type="hidden"></input> with Base64 data of resulted image (client's photo + product)
     */
    static private final String RESULT_IMAGE_DATA_INPUT_ID = "sy-result-image-input";
    /**
     * width of resulted image. Attribute of element with RESULT_IMAGE_DATA_INPUT_ID
     */
    static private final String RESULT_IMAGE_WIDTH_ATTRIBUTE = "syimagewidth";

    private Canvas canvas;
    private String windowFrameData;
    private PictureDto pictureDto;

    private InputElement resultImageDataElement;

    ProcessPhotoHandler(Canvas canvas, String windowFrameData, PictureDto pictureDto) {
        this.canvas = canvas;
        this.windowFrameData = windowFrameData;
        this.pictureDto = pictureDto;

        processExternalElements();
    }

    public void process(CCImage clientPhoto) {
        canvas.setCoordinateSpaceHeight(Math.min(canvas.getCoordinateSpaceHeight(), clientPhoto.getImageRectangle().height));
        showProduct(clientPhoto);
    }

    private void removeLoadingBackgroundSign() {
        canvas.getParent().removeStyleName(ProcessedPhotoWidget.LOADING_BACKGROUND_STYLE);
    }

    /**
     * Shows on user's photo new image. id may be null
     *
     * @param clientPhoto client's photo
     */
    private void showProduct(final CCImage clientPhoto) {
        final WindowFrameList frameList = new WindowFrameList(canvas);
        frameList.setDataString(windowFrameData, clientPhoto.getImageRectangle(), pictureDto.getKx(), pictureDto.getKy());

        if (frameList.isFirstFrameFull()) {
            //remove incorrect last frame, if needed
            if (!frameList.isCorrect()) {
                frameList.removeFrame();
            }

            //with GWTBufferedImage
            ImagePreloader.load(pictureDto.getImageUrl(), new ImageLoadHandler() {
                public void imageLoaded(ImageLoadEvent event) {
                    final GWTBufferedImage srcImage = ImageUtils.imageToGWTBufferedImage(event.takeImage());
                    boolean isClientPhotoDrawn = false;

                    for (WindowFrame frame : frameList.getFrameList()) {
                        if (!isClientPhotoDrawn) {
                            clientPhoto.draw();
                            isClientPhotoDrawn = true;
                        }

                        if (GWT.isProdMode()) {
                            GWTBufferedImage distortedImage = ImageUtils.distortProductToFrame(
                                    srcImage, frame);
                            CCCanvas product = new CCCanvas(canvas, distortedImage.getImageData(),
                                    frame.getMinX(),
                                    frame.getMinY(),
                                    false);
                            product.draw();
                        } else {
                            frame.draw();
                        }
                    }

                    removeLoadingBackgroundSign();

                    saveResultImageData(clientPhoto);
                }
            });
        }
    }

    private void processExternalElements() {
        Element ride = Document.get().getElementById(RESULT_IMAGE_DATA_INPUT_ID);
        if (ride != null) {
            resultImageDataElement = InputElement.as(ride);
        }
    }

    private void saveResultImageData(CCImage clientPhoto) {
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
}
