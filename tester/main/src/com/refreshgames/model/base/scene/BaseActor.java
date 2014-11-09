package com.refreshgames.model.base.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.refreshgames.model.modeldata.ModelData;
import com.refreshgames.view.mediadata.MediaData;

/**
 * ancestor of actors
 */
public abstract class BaseActor<B extends ModelData, M extends MediaData> extends Actor {
    /**
     * all info about body in box2d world
     */
    protected B modelData;
    /**
     * contains all info needed for mediaData
     */
    protected M mediaData;

    protected BaseActor() {
    }

    protected BaseActor(B modelData, M mediaData) {
        init(modelData, mediaData);
    }

    protected void init(B bodyData, M mediaData) {
        this.mediaData = mediaData;
        this.modelData = bodyData;
        this.modelData.connectWithActor(this);

        createListeners();
    }

    protected void createListeners() {
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        mediaData.act(delta);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha); //TODO: optimization remove

        mediaData.draw(batch);
    }

    public B getModelData() {
        return modelData;
    }
}
