package com.refreshgames.controller;

import com.badlogic.gdx.utils.Pools;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.model.listeners.AccelerometerListener;
import com.refreshgames.other.debug.DebugUtils;
import com.refreshgames.other.debug.DebugValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Main controller
 */
public class WorldController {
    private float jumpVelocity;
    private BaseStage stage;
    private List<DebugValue> debugValueList;

    public WorldController(BaseStage stage) {
        jumpVelocity = 0;
        this.stage = stage;
        if (DebugUtils.DEBUG_DETECTOR) {
            this.debugValueList = new ArrayList<DebugValue>();
        }
    }

    public void jump(float jumpVelocity) {
        this.jumpVelocity = jumpVelocity;
    }

    public void processInput(float deltaTime) {
        if (jumpVelocity > 0) {
            AccelerometerListener.AccelerometerEvent accelerometerEvent =
                    Pools.obtain(AccelerometerListener.AccelerometerEvent.class);
            accelerometerEvent.setState(AccelerometerListener.EventState.JUMP);
            accelerometerEvent.setValue(jumpVelocity);
            stage.getRoot().fire(accelerometerEvent);
            //DebugUtils.addDebugMessage("ImpulseUp");
        }
        jumpVelocity = 0;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return stage.touchDown(screenX, screenY, pointer, button);
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return stage.touchDragged(screenX, screenY, pointer);
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX, screenY, pointer, button);
    }
}
