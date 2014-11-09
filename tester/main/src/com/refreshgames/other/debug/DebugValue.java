package com.refreshgames.other.debug;

import com.badlogic.gdx.graphics.Color;

/**
 * One value on chart
 */
public class DebugValue {
    private float realValue;
    private float adoptedValue;
    private Color color;

    public DebugValue(float realValue, float adoptedValue, Color color) {
        this.realValue = realValue;
        this.adoptedValue = adoptedValue;
        this.color = color;
    }

    public float getRealValue() {
        return realValue;
    }

    public float getAdoptedValue() {
        return adoptedValue;
    }

    public Color getColor() {
        return color;
    }
}
