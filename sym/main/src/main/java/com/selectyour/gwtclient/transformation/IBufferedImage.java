package com.selectyour.gwtclient.transformation;

/**
 * Mimic needed functions of awt.BufferedImage
 */
public interface IBufferedImage {
    /**
     * constructs empty image with width and height
     */
    public IBufferedImage constructImage(int width, int height);

    /**
     * Returns the width of the <code>BufferedImage</code>.
     *
     * @return the width of this <code>BufferedImage</code>
     */
    public int getWidth();

    /**
     * Returns the width of the <code>BufferedImage</code>.
     *
     * @return the width of this <code>BufferedImage</code>
     */
    public int getHeight();

    //returns array where pixel is defined with 4 consecutive values RGBA
    public IPixels getPixels();

}
