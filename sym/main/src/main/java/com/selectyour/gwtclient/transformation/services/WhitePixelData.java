package com.selectyour.gwtclient.transformation.services;

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.ImageData;

public class WhitePixelData {
    /**
     * max depth from border to search for white pixels
     */
    static private final int MAX_DEPTH = 5;
    /**
     * number of dots to analyze average color
     */
    static private final int ANALYZED_DOTS_CNT = 5;
    static private float K_MAX_COLOR_DIFFERENCE = 0.3f;
    private final ImageData imageData;
    private final CanvasPixelArray pixelArray;
    private final int imageWidth;
    private final int imageHeight;

    private int pixelsTop;
    private int pixelsBottom;
    private int pixelsLeft;
    private int pixelsRight;

    public WhitePixelData(ImageData imageData) {
        this.imageData = imageData;
        this.pixelArray = imageData.getData();
        this.imageWidth = imageData.getWidth();
        this.imageHeight = imageData.getHeight();

        process();
    }

    private void process() {
        //10pixels from top border
        float mainColor = calcAvgColor(0, 10, imageWidth, 1);

        //TODO: move in universal method
        //top
        pixelsTop = 0;
        while (pixelsTop < MAX_DEPTH) {
            float color = calcAvgColor(0, pixelsTop, imageWidth, 1);
            if (!shouldMakeInvisible(mainColor, color)) {
                break;
            }
            pixelsTop++;
        }

        //bottom
        pixelsBottom = 0;
        while (pixelsBottom < MAX_DEPTH) {
            float color = calcAvgColor(0, imageHeight - pixelsBottom - 1, imageWidth, 1);
            if (!shouldMakeInvisible(mainColor, color)) {
                break;
            }
            pixelsBottom++;
        }

        //left
        pixelsLeft = 0;
        while (pixelsLeft < MAX_DEPTH) {
            float color = calcAvgColor(pixelsLeft, 0, 1, imageHeight);
            if (!shouldMakeInvisible(mainColor, color)) {
                break;
            }
            pixelsLeft++;
        }

        //right
        pixelsRight = 0;
        while (pixelsRight < MAX_DEPTH) {
            float color = calcAvgColor(imageWidth - pixelsRight - 1, 0, 1, imageHeight);
            if (!shouldMakeInvisible(mainColor, color)) {
                break;
            }
            pixelsRight++;
        }
    }

    /**
     * returns average (Red + Green + Blue) for non transparent pixels. If count of transparent pixels >= (ANALYZED_DOTS_CNT / 2)
     * then returns -1
     *
     * @param xBeg   begin pixel
     * @param yBeg
     * @param width  data to calc end pixel
     * @param height
     * @return
     */
    private float calcAvgColor(int xBeg, int yBeg, int width, int height) {
        int xStep = width / (ANALYZED_DOTS_CNT - 1);
        int yStep = height / (ANALYZED_DOTS_CNT - 1);
        if (xStep == 0) {
            xStep = 1;
        }
        if (yStep == 0) {
            yStep = 1;
        }

        int sumColors = 0, opaqueCnt = 0;
        int transparentCnt = 0;

        for (int x = xBeg; x < (xBeg + width); x += xStep) {
            for (int y = yBeg; y < (yBeg + height); y += yStep) {
                int sumColor = getSumColor(x, y);
                if (sumColor > 0) {
                    sumColors += sumColor;
                    opaqueCnt++;
                } else {
                    transparentCnt++;
                }
            }
        }

        if (transparentCnt > (ANALYZED_DOTS_CNT / 2)) {
            return -1;
        } else {
            return ((float) sumColors) / opaqueCnt;
        }
    }

    /**
     * returns Red + Green + Blue or -1, if alpha = 0 (transparent pixel)
     *
     * @param x
     * @param y
     * @return
     */
    private int getSumColor(int x, int y) {
        int start = getPosition(x, y);
        int sum = 0;
        int offset = 0;
        while (offset < 3) {
            sum += pixelArray.get(start + offset);

            offset++;
        }

        if (pixelArray.get(start + offset) != 0) { //check alpha
            return sum;
        } else {
            return -1;
        }
    }

    private int getPosition(int x, int y) {
        return 4 * (x + y * imageWidth);
    }

    private boolean shouldMakeInvisible(float mainColor, float currentColor) {
        return ((currentColor - mainColor) / mainColor) > K_MAX_COLOR_DIFFERENCE;
    }

    public int getPixelsTop() {
        return pixelsTop;
    }

    public int getPixelsHeight() {
        return pixelsTop + pixelsBottom;
    }

    public int getPixelsLeft() {
        return pixelsLeft;
    }

    public int getPixelsWidth() {
        return pixelsLeft + pixelsRight;
    }
}
