package com.selectyour.gwtclient.transformation.services;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.selectyour.gwtclient.component.canvas.WindowFrame;
import com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader.ImagePreloader;
import com.selectyour.gwtclient.transformation.GWTBufferedImage;
import com.selectyour.gwtclient.transformation.IBufferedImage;
import com.selectyour.gwtclient.transformation.IntPoint;
import com.selectyour.gwtclient.transformation.base.Rectangle;
import com.selectyour.gwtclient.transformation.filters.PerspectiveFilter;

/**
 * Service for Image transformations
 */
public class ImageUtils {
    /**
     * window frame will be increased on this count of pixels (to overlap picture on photo)
     */
    private static int FRAME_DISTAL_LENGTH = 0;

    /**
     * scales image from srcCanvas, that is diplayed in rectangle to new canvas with width.
     * Returns this new canvas
     *
     * @param srcCanvas
     * @param rectangle
     * @param width
     * @return
     */
    static public Canvas scaleCanvasToWidth(Canvas srcCanvas, Rectangle rectangle, int width) {
        Canvas dstCanvas = Canvas.createIfSupported();
        if (dstCanvas != null) {
            dstCanvas.setCoordinateSpaceWidth(width);
            dstCanvas.setCoordinateSpaceHeight(rectangle.height * width / rectangle.width);
            Context2d dstContext2d = dstCanvas.getContext2d();
            dstContext2d.drawImage(srcCanvas.getCanvasElement(),
                    rectangle.x, rectangle.y, rectangle.width, rectangle.height,
                    0, 0, dstCanvas.getCoordinateSpaceWidth(), dstCanvas.getCoordinateSpaceHeight());
            return dstCanvas;
        } else {
            return null;
        }
    }

    static public GWTBufferedImage imageToGWTBufferedImage(Image image) {
        ImageData imageData = imageToData(image);
        return new GWTBufferedImage(imageData);
    }

    static public ImageData imageToData(Image image) {
        Context2d context = getContextForImageOnCanvas(image);
        ImageData imageData = context.getImageData(0, 0, image.getWidth(), image.getHeight());

        //TODO: !remove after saveing all images in own database. Images in database shouldnt contain white pixels
        WhitePixelData whitePixelData = new WhitePixelData(imageData);
        imageData = context.getImageData(whitePixelData.getPixelsLeft(), whitePixelData.getPixelsTop(),
                image.getWidth() - whitePixelData.getPixelsWidth(), image.getHeight() - whitePixelData.getPixelsHeight());

        return imageData;
    }

    static private Context2d getContextForImageOnCanvas(Image image) {
        Canvas canvasTmp = Canvas.createIfSupported();
        canvasTmp.setCoordinateSpaceHeight(image.getHeight());
        canvasTmp.setCoordinateSpaceWidth(image.getWidth());
        Context2d context = canvasTmp.getContext2d();

        ImageElement imageElement = ImageElement.as(image.getElement());
        context.drawImage(imageElement, 0f, 0f);
        return context;
    }

    static public void dataToImageCallback(ImageData imageData, ImageLoadHandler loadHandler) {
        Canvas canvasTmp = Canvas.createIfSupported();
        canvasTmp.setCoordinateSpaceHeight(imageData.getHeight());
        canvasTmp.setCoordinateSpaceWidth(imageData.getWidth());
        Context2d context = canvasTmp.getContext2d();
        context.putImageData(imageData, 0, 0);

        ImagePreloader.load(canvasTmp.toDataUrl("image/jpg"), loadHandler);
    }

    /**
     * distort product image (from productImageUrl) to frame with GWTBufferedImage
     *
     * @param srcImage
     * @param frame
     * @return
     */
    static public GWTBufferedImage distortProductToFrame(GWTBufferedImage srcImage, final WindowFrame frame) {
        IBufferedImage dstImage = null;
        PerspectiveFilter filter = new PerspectiveFilter();
        IntPoint topLeft = frame.getPoint(WindowFrame.TOP_LEFT);
        IntPoint topRight = frame.getPoint(WindowFrame.TOP_RIGHT);
        IntPoint bottomRight = frame.getPoint(WindowFrame.BOTTOM_RIGHT);
        IntPoint bottomLeft = frame.getPoint(WindowFrame.BOTTOM_LEFT);
        filter.setCorners(
                topLeft.getX(),
                topLeft.getY(),
                topRight.getX(),
                topRight.getY(),
                bottomRight.getX(),
                bottomRight.getY(),
                bottomLeft.getX(),
                bottomLeft.getY());
        return (GWTBufferedImage) filter.filter(srcImage, dstImage);
    }

    static public void removeColor(ImageData imageData) {
        int sourceWidth = imageData.getWidth();
        int sourceHeight = imageData.getHeight();

        for (int y = 0; y < sourceHeight; y++) {
            // loop through each column
            for (int x = 0; x < sourceWidth; x++) {
                int red = imageData.getRedAt(x, y);
                int green = imageData.getGreenAt(x, y);
                int blue = imageData.getBlueAt(x, y);
                int gray = (red + green + blue) / 3;
                imageData.setRedAt(gray, x, y);
                imageData.setGreenAt(gray, x, y);
                imageData.setBlueAt(gray, x, y);
            }
        }

        /*// to quickly iterate over all pixels, use a for loop like this
        for (var i = 0; i < data.length; i += 4) {
            var red = data[i]; // red
            var green = data[i + 1]; // green
            var blue = data[i + 2]; // blue
            // i+3 is alpha (the fourth element)
        }

        // or iterate over all pixels based on x and y coordinates
        // like this
        for (var y = 0; y < sourceHeight; y++) {
            // loop through each column
            for (var x = 0; x < sourceWidth; x++) {
                var red = data[((sourceWidth * y) + x) * 4];
                var green = data[((sourceWidth * y) + x) * 4 + 1];
                var blue = data[((sourceWidth * y) + x) * 4 + 2];
            }
        }*/
    }

    static public void makeTriangleInvisible(ImageData imageData) {
        int sourceWidth = imageData.getWidth();
        int sourceHeight = imageData.getHeight();

        for (int y = 0; y < sourceHeight; y++) {
            for (int x = 0; x < sourceWidth; x++) {
                if (y > x) {
                    imageData.setAlphaAt(0, x, y);
                }
            }
        }
    }
}
