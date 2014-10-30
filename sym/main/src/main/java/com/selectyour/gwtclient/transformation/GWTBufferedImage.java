package com.selectyour.gwtclient.transformation;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.ImageData;

/**
 * IBufferedImage implementation on ImageData
 */
public class GWTBufferedImage implements IBufferedImage {
    private ImageData imageData;

    public GWTBufferedImage(ImageData imageData) {
        this.imageData = imageData;
    }

    public IBufferedImage constructImage(int width, int height) {
        Canvas canvas = Canvas.createIfSupported();
        if (canvas != null) {
            canvas.setCoordinateSpaceWidth(width);
            canvas.setCoordinateSpaceHeight(height);

            return new GWTBufferedImage(canvas.getContext2d().getImageData(0, 0, width, height));
        } else {
            return null;
        }
    }

    public int getWidth() {
        return imageData.getWidth();
    }

    public int getHeight() {
        return imageData.getHeight();
    }

    public IPixels getPixels() {
        return new GWTPixels(imageData.getData());
    }

    public ImageData getImageData() {
        return imageData;
    }
}
