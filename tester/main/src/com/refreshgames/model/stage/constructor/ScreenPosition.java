package com.refreshgames.model.stage.constructor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * Position on screen
 */
public class ScreenPosition extends Vector3 {
    private Camera camera;

    public ScreenPosition(Camera camera) {
        this.camera = camera;
        this.z = 0;
    }

    public void setUnproject(float x, float y) {
        set(x, y, this.z);
        camera.unproject(this);
    }
}
