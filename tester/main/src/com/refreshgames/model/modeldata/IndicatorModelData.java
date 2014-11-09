package com.refreshgames.model.modeldata;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.refreshgames.view.mediadata.SpriteModel;

//TODO: remove
public class IndicatorModelData extends EmptyModelData implements SpriteModel {
    private static final float HALF_WIDTH = 0.5f;
    //private Transform transform = new Transform()

    @Override
    public float getHalfHeight() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float getHalfWidth() {
        return HALF_WIDTH;
    }

    @Override
    public Transform getTransform() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Vector2 getCenter() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float getAngleDegree() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
