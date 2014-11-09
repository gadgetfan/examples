package com.refreshgames.view.mediadata;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.refreshgames.model.modeldata.PartModelData;

/**
 * Renders animation
 */
public class AnimationMediaData extends SpriteMediaData {
    private PartModelData bodyData;
    private Animation animation;
    private float stateTime;

    public AnimationMediaData(TextureAtlas atlas, float frameDuration, String animationGroupName, int playMode) {
        super();

        animation = loadAnimation(atlas, frameDuration, animationGroupName, playMode);
        stateTime = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);    //TODO: optimization remove

        stateTime += delta;
    }

    @Override
    protected void internalDraw(SpriteBatch batch, float x, float y, float width, float height,
                                float rotation) {
        batch.draw(animation.getKeyFrame(stateTime), x, y,
                0, 0, width, height,
                1, 1, rotation);
    }

    private Animation loadAnimation(TextureAtlas atlas, float frameDuration, String animationGroupName,
                                    int playMode) {
        final int MIN_INDEX = 0;

        Array<TextureRegion> textureRegionArray = new Array<TextureRegion>();
        int i = MIN_INDEX;
        while (true) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(animationGroupName, i++);
            if (region != null) {
                textureRegionArray.add(new TextureAtlas.AtlasRegion(region));
            } else {
                break;
            }
        }

        if (textureRegionArray.size <= 0) {
            throw new IllegalArgumentException("Cannot find animationGroupName " + animationGroupName);
        }
        return new Animation(frameDuration, textureRegionArray, playMode);
    }
}
