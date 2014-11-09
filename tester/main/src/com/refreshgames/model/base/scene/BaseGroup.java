package com.refreshgames.model.base.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.refreshgames.model.modeldata.EmptyModelData;
import com.refreshgames.view.mediadata.EmptyMediaData;

import java.util.ArrayList;
import java.util.List;

/**
 * Ancestor for all groups
 */
public class BaseGroup extends BaseActor<EmptyModelData, EmptyMediaData> {
    private List<BaseActor> children = new ArrayList<BaseActor>();

    public float halfWidth;
    public float halfHeight;
    public Vector2 center = new Vector2();
    public float left;
    public float bottom;


    public BaseGroup(float x, float y, float halfWidth, float halfHeight) {
        super(new EmptyModelData(), new EmptyMediaData());

        this.center.x = x;
        this.center.y = y;
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;

        this.left = x - halfWidth;
        this.bottom = y - halfHeight;
    }

    /**
     * now I can ONLY add actor. If will be removing actor addTransform is needed too
     *
     * @param actor
     */
    public void addActor(BaseActor actor) {
        children.remove(actor);
        children.add(actor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        for (BaseActor actor : children) {
            actor.act(delta);
        }
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (BaseActor actor : children) {
            actor.draw(batch, parentAlpha);
        }
    }
}
