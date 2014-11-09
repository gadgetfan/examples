package com.refreshgames.controller.inputprocessor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.refreshgames.other.debug.DebugValueSender;

/**
 * Processes input for desktop
 */
public class DesktopInputProcessor extends BaseInputProcessor {
    private DebugValueSender debugXValue = new DebugValueSender(480, new Color(0, 0, 1, 1));

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.DPAD_UP:
                controller.jump(2);
                break;
        }

        return true;
    }
}
