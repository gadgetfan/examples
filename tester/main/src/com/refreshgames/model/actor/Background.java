package com.refreshgames.model.actor;

import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.model.modeldata.EmptyModelData;
import com.refreshgames.view.mediadata.BackgroundMediaData;

/**
 * Actor to make background
 */
public class Background extends BaseActor<EmptyModelData, BackgroundMediaData> {
    public Background(BaseStage stage) {
        super(new EmptyModelData(), new BackgroundMediaData(stage, "background/tile"));
    }

    /*@Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }*/
}
