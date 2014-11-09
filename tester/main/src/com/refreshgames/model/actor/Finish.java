package com.refreshgames.model.actor;

import com.refreshgames.model.actor.robot.Cabin;
import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.model.listeners.CollisionListener;
import com.refreshgames.model.modeldata.Collisions;
import com.refreshgames.model.modeldata.Material;
import com.refreshgames.model.stage.SavedActorList;
import com.refreshgames.model.stage.tester.TesterStage;
import com.refreshgames.view.assets.Assets;
import com.refreshgames.view.mediadata.TextureMediaData;

/**
 * Beam to construct all
 */
public class Finish extends Part implements SavedActorList.SaveAble {
    public static final float HALF_WIDTH = 2f;
    public static final float HALF_HEIGHT = 3f;

    public Finish(BaseStage stage, float x, float y) {
        //TODO: use animation to rotate wheel, do not rotate texture
        super(new TextureMediaData(stage.getAtlas(), "finish/finish"),
                stage.getWorld().STATIC_BODY_DEF.createAsRectangle(Material.PLASTIC,
                        x, y, HALF_WIDTH, HALF_HEIGHT, 0));


        getModelData().makeSensor();
        getModelData().setCollisionFilter(Collisions.BORDER_FILTER);
    }

    @Override
    protected void createListeners() {
        addListener(new CollisionListener() {
            @Override
            public boolean onCollision(BaseActor collidedActor, float impulse) {
                if (collidedActor instanceof Cabin) {
                    finishLevel();
                }

                return true;
            }
        });
    }

    private void finishLevel() {
        Assets.VICTORY.playStandard();
        ((BaseStage) getStage()).lifeCycleCode = TesterStage.LEVEL_FINISHED_EXIT;
    }
}
