package com.refreshgames.view.mediadata;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.refreshgames.model.base.scene.BaseStage;

/**
 * Renders background with several textures with parallax effect
 */
public class BackgroundMediaData extends EmptyMediaData {
    private static final float WIDTH = 5;
    private static final float HEIGHT = 4;

    private TextureAtlas.AtlasRegion region;
    private Camera camera;
    private float halfStageWidth;
    private float halfStageHeight;

    public BackgroundMediaData(BaseStage stage, String regionName) {
        this.region = stage.getAtlas().findRegion(regionName);
        this.camera = stage.getCamera();
        this.halfStageWidth = stage.getWidth() / 2;
        this.halfStageHeight = stage.getHeight() / 2;
    }

    public void draw(SpriteBatch batch) {
        //TODO: optimization use MathUtils.floorPositive
        float xStart = WIDTH * MathUtils.floor((camera.position.x - halfStageWidth) / WIDTH);
        float yStart = HEIGHT * MathUtils.floor((camera.position.y - halfStageHeight) / HEIGHT);
        float xFinish = camera.position.x + halfStageWidth;
        float yFinish = camera.position.y + halfStageHeight;

        batch.disableBlending();
        for (float x = xStart; x < xFinish; x += WIDTH) {
            for (float y = yStart; y < yFinish; y += HEIGHT) {
                batch.draw(region, x, y, WIDTH, HEIGHT);
            }
        }
        batch.enableBlending();
    }
}
