package com.refreshgames.view.mediadata;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Renders texture
 */
public class TextureArrayMediaData extends SpriteMediaData {
    private TextureAtlas.AtlasRegion[] textureRegions;
    private int index = 0;

    public TextureArrayMediaData(TextureAtlas atlas, String... regionNames) {
        super();

        textureRegions = new TextureAtlas.AtlasRegion[regionNames.length];
        for (int i = 0; i < regionNames.length; ++i) {
            textureRegions[i] = atlas.findRegion(regionNames[i]);
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    protected void internalDraw(SpriteBatch batch, float x, float y, float width, float height,
                                float rotation) {
        batch.draw(textureRegions[index], x, y,
                0, 0, width, height,
                1, 1, rotation);
    }
}
