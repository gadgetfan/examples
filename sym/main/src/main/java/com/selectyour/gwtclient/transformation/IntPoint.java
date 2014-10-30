package com.selectyour.gwtclient.transformation;

/**
 * like Point, but with integer x, y
 */
public class IntPoint {
    private int x;
    private int y;

    public IntPoint(int x, int y) {
        setXY(x, y);
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void scale(float kx, float ky) {
        setXY(Math.round(x * kx), Math.round(y * ky));
    }
}
