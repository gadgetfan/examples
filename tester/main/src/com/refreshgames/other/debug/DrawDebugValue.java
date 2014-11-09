package com.refreshgames.other.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * debug value to draw on screen
 */
public class DrawDebugValue extends Vector2 {
    private Color color;

    public DrawDebugValue(float x, float y, Color color) {
        super(x, y);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
