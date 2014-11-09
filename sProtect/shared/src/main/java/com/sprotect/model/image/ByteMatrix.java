package com.sprotect.model.image;

import java.nio.ByteBuffer;

/**
 * Matrix of pixels black/white
 */
public class ByteMatrix {
    private static final byte BLACK = 0;
    private static final byte WHITE = -1;
    private static final byte GRAY = -2;

    private final boolean isArray; //is needed because of android issue https://code.google.com/p/android/issues/detail?id=34576
    private final ByteBuffer buffer;
    private final int width;
    private final int height;

    public ByteMatrix(int width, int height, ByteBuffer buffer) {
        isArray = false;
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }

    public ByteMatrix(int width, int height, byte[] buffer) {
        isArray = true;
        this.width = width;
        this.height = height;
        this.buffer = ByteBuffer.wrap(buffer);
    }

    public byte[] getBuffer() {
        if (isArray) {
            return buffer.array();
        } else {
            byte[] byteArray = new byte[getLength()];
            buffer.get(byteArray, 0, getLength());

            return byteArray;
        }
    }

    private int getLength() {
        return width * height;
    }

    public boolean isBlack(int x, int y) {
        return (buffer.get(getOffset(x, y)) == BLACK);
    }

    private int getOffset(int x, int y) {
        return y * width + x;
    }

    public boolean isWhite(int x, int y) {
        return (buffer.get(getOffset(x, y)) == WHITE);
    }

    public void setBlack(int x, int y) {
        buffer.put(getOffset(x, y), BLACK);
    }

    public void setGray(int x, int y) {
        buffer.put(getOffset(x, y), GRAY);
    }

    public void setWhite(int x, int y) {
        buffer.put(getOffset(x, y), WHITE);
    }

    /**
     * draws points array: x0, y0, x1, y1, x2, y2...
     *
     * @param points
     */
    public void setWhite(float[] points) {
        assert (points.length / 2 == 0);

        for (int i = 0; i < points.length; i += 2) {
            setWhite(Math.round(points[i]), Math.round(points[i + 1]));
        }
    }

    public void setColor(int x, int y, byte color) {
        buffer.put(getOffset(x, y), color);
    }

    public void setColor(float[] points, byte color) {
        assert (points.length / 2 == 0);

        for (int i = 0; i < points.length; i += 2) {
            setColor(Math.round(points[i]), Math.round(points[i + 1]), color);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
