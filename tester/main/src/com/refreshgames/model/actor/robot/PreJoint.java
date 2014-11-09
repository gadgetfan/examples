package com.refreshgames.model.actor.robot;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.model.modeldata.aperturejoint.PreJointModelData;
import com.refreshgames.view.mediadata.TextureMediaData;

/**
 * Temporal joint between parts
 */
public class PreJoint extends BaseActor<PreJointModelData, TextureMediaData> {
    public PreJoint(BaseStage stage, Fixture activeFixture, Fixture passiveFixture) {
        super(new PreJointModelData(activeFixture, passiveFixture),
                new TextureMediaData(stage.getAtlas(), "part/prenut"));

        mediaData.setSpriteModel(modelData);

        getModelData().getActiveFixture().setUserData(this);
        getModelData().getPassiveFixture().setUserData(this);
    }

    /**
     * removes data about this preaperure
     */
    public void dispose() {
        modelData.dispose();
    }
}
