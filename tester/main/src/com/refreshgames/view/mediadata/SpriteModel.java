package com.refreshgames.view.mediadata;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;

/**
 * model to draw Sprite
 */
public interface SpriteModel {

    public float getHalfHeight();

    public float getHalfWidth();

    public abstract Transform getTransform();

    public abstract Vector2 getCenter();

    public abstract float getAngleDegree();
}
