package com.refreshgames.other.debug;

import com.badlogic.gdx.graphics.Color;

/**
 * help to produce DebugValue
 */
public class DebugValueSender {
    private float kValue;
    private Color color;

    public DebugValueSender(float maxAbsValue, Color color) {
        this.kValue = 1 / Math.abs(maxAbsValue * 2);
        this.color = color;
    }

    public void sendValue(float realValue) {
        if (DebugUtils.DEBUG_DETECTOR) {
            DebugUtils.sendDebugValue(new DebugValue(realValue, realValue * kValue, color));
        }
    }
}
