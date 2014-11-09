package com.refreshgames.controller.inputprocessor;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.refreshgames.controller.WorldController;

/**
 * ancestor of input processors
 */
public abstract class BaseInputProcessor extends InputAdapter implements InputProcessor {
    protected WorldController controller;

    protected BaseInputProcessor() {
    }

    public void setController(WorldController controller) {
        this.controller = controller;
    }

    /**
     * Accelerometer event. TODO: Should be incorporated in InputProcessor
     *
     * @param x
     * @param y
     * @param z
     * @param deltaTime
     * @return
     */
    public boolean accelerometerChanged(float x, float y, float z, float deltaTime) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return controller.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return controller.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return controller.touchUp(screenX, screenY, pointer, button);
    }
}
