package com.refreshgames.model.modeldata;

import com.refreshgames.view.mediadata.SpriteModel;

/**
 * model for objects from Box2d world
 */
public abstract class Box2dModelData extends EmptyModelData implements SpriteModel {
    protected final float halfWidth;
    protected final float halfHeight;

    public Box2dModelData(float halfWidth, float halfHeight) {
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public abstract void setAngleDegree(float angleDegree);
}
