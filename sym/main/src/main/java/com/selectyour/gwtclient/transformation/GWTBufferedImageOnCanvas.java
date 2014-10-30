package com.selectyour.gwtclient.transformation;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

/**
 * IBufferedImage implementation on Canvas
 */
public class GWTBufferedImageOnCanvas implements IBufferedImage {
    private Canvas canvas;
    private ImageData imageData;

    public GWTBufferedImageOnCanvas(int width, int height) {
        canvas = Canvas.createIfSupported();
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        imageData = canvas.getContext2d().getImageData(0, 0, width, height);
    }

    public GWTBufferedImageOnCanvas(Image image) {
        canvas = Canvas.createIfSupported();
        canvas.setCoordinateSpaceWidth(image.getWidth());
        canvas.setCoordinateSpaceHeight(image.getHeight());
        canvas.getContext2d().drawImage(ImageElement.as(image.getElement()), 0, 0);
        imageData = canvas.getContext2d().getImageData(0, 0, image.getWidth(), image.getHeight());
    }

    public IBufferedImage constructImage(int width, int height) {
        if (canvas != null) {
            return new GWTBufferedImageOnCanvas(width, height);
        } else {
            return null;
        }
    }

    public int getWidth() {
        return canvas.getCoordinateSpaceWidth();
    }

    public int getHeight() {
        return canvas.getCoordinateSpaceHeight();
    }

    public IPixels getPixels() {
        return new GWTPixels(imageData.getData());
    }

    public ImageData getImageData() {
        return imageData;
    }

    public CanvasElement getCanvasElement() {
        return canvas.getCanvasElement();
    }

    public void refreshByData() {
        canvas.getContext2d().putImageData(imageData, 0, 0);
    }
}
