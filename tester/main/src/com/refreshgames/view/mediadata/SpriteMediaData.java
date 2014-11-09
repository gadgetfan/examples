package com.refreshgames.view.mediadata;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Transform;

/**
 * Renders sprite
 */
public abstract class SpriteMediaData extends EmptyMediaData {
    private SpriteModel spriteModel;

    public SpriteMediaData() {
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);    //TODO: optimization remove

        Transform transform = spriteModel.getTransform();

        //Below is exert from Transform.mul()
        float x = transform.vals[Transform.POS_X] + transform.vals[Transform.COS] * -spriteModel.getHalfWidth()
                + -transform.vals[Transform.SIN] * -spriteModel.getHalfHeight();
        float y = transform.vals[Transform.POS_Y] + transform.vals[Transform.SIN] * -spriteModel.getHalfWidth()
                + transform.vals[Transform.COS] * -spriteModel.getHalfHeight();

        float rotation = spriteModel.getAngleDegree();

        internalDraw(batch, x, y, spriteModel.getHalfWidth() * 2, spriteModel.getHalfHeight() * 2, rotation);
    }

    protected abstract void internalDraw(SpriteBatch batch, float x, float y, float width, float height,
                                         float rotation);

    public void setSpriteModel(SpriteModel spriteModel) {
        this.spriteModel = spriteModel;
    }
}
