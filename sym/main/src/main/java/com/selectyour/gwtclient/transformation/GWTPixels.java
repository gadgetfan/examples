package com.selectyour.gwtclient.transformation;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;

public class GWTPixels implements IPixels {
    /**
     * Offsets for each color use RGBA ordering.
     */
    private static final int OFFSET_RED = 0;
    private static final int OFFSET_GREEN = 1;
    private static final int OFFSET_BLUE = 2;
    private static final int OFFSET_ALPHA = 3;

    private static final int SHIFT_RED = 16;
    private static final int SHIFT_GREEN = 8;
    private static final int SHIFT_BLUE = 0;
    private static final int SHIFT_ALPHA = 24;

    private CanvasPixelArray canvasPixelArray;

    public GWTPixels(CanvasPixelArray canvasPixelArray) {
        this.canvasPixelArray = canvasPixelArray;
    }

    public int get(int index) {
        return canvasPixelArray.get(index * 4 + OFFSET_RED) << SHIFT_RED
                | canvasPixelArray.get(index * 4 + OFFSET_GREEN) << SHIFT_GREEN
                | canvasPixelArray.get(index * 4 + OFFSET_BLUE) << SHIFT_BLUE
                | canvasPixelArray.get(index * 4 + OFFSET_ALPHA) << SHIFT_ALPHA;
    }

    public void set(int index, int value) {
        canvasPixelArray.set(index * 4 + OFFSET_RED, (value >> SHIFT_RED) & 0xFF);
        canvasPixelArray.set(index * 4 + OFFSET_GREEN, (value >> SHIFT_GREEN) & 0xFF);
        canvasPixelArray.set(index * 4 + OFFSET_BLUE, (value >> SHIFT_BLUE) & 0xFF);
        canvasPixelArray.set(index * 4 + OFFSET_ALPHA, (value >> SHIFT_ALPHA) & 0xFF); //TODO: set for all pixels one time for optimisation ?
    }

    public void setTransparent(int index) {
        canvasPixelArray.set(index * 4 + OFFSET_ALPHA, 0);
    }
}
