package com.selectyour.gwtclient.transformation;

/**
 * Array of pixels, transforms GWT.CanvasPixelArray (RGBA)
 * to get pixel value as 32bit int ARGB
 */
public interface IPixels {
    /**
     * returns pixel in ARGB-int value
     *
     * @param index
     * @return
     */
    public int get(int index);

    /**
     * sets pixel in ARGB-int value
     *
     * @param index
     * @param value
     */
    public void set(int index, int value);

    /**
     * sets pixel transparent
     *
     * @param index
     */
    public void setTransparent(int index);
}
