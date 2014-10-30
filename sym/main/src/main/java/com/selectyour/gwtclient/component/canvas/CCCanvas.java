package com.selectyour.gwtclient.component.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.CanvasElement;

/**
 * Internal component is canvas element
 */
public class CCCanvas extends AbstractStretchedComponent {
    private CanvasElement canvasElement;

    public CCCanvas(Canvas canvas, ImageData imageData, int x, int y, boolean stretch) {
        super(canvas, x, y, stretch);
        Canvas tmpCanvas = Canvas.createIfSupported();
        tmpCanvas.setCoordinateSpaceWidth(imageData.getWidth());
        tmpCanvas.setCoordinateSpaceHeight(imageData.getHeight());
        tmpCanvas.getContext2d().putImageData(imageData, 0, 0);
        this.canvasElement = tmpCanvas.getCanvasElement();
    }

    @Override
    public int getHeight() {
        return canvasElement.getHeight();
    }

    @Override
    public int getWidth() {
        return canvasElement.getWidth();
    }

    @Override
    protected void internalDrawElement(Context2d context, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh) {
        context.drawImage(canvasElement, sx, sy, sw, sh, dx, dy, dw, dh);
    }
}
