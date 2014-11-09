package com.refreshgames.view.mediadata;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Beam
 */
public class BeamTextureMediaData extends TextureMediaData {
    private static final String REGION_PATH = "part/beam";

    public BeamTextureMediaData(TextureAtlas atlas, int apertureCnt) {
        super(atlas, REGION_PATH + apertureCnt);
    }
}
