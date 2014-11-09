package com.refreshgames.view.mediadata;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TextureMediaData extends SpriteMediaData {
    private TextureAtlas.AtlasRegion textureRegion;

    public TextureMediaData(TextureAtlas atlas, String regionName) {
        super();

        textureRegion = atlas.findRegion(regionName);
    }

    @Override
    protected void internalDraw(SpriteBatch batch, float x, float y, float width, float height,
                                float rotation) {
        batch.draw(textureRegion, x, y,
                0, 0, width, height,
                1, 1, rotation);
    }
}
