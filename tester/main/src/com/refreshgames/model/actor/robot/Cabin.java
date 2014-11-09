package com.refreshgames.model.actor.robot;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.refreshgames.model.GameWorld;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.model.modeldata.Collisions;
import com.refreshgames.model.modeldata.Material;
import com.refreshgames.view.mediadata.TextureMediaData;

/**
 * Cabin of robot
 */
public class Cabin extends RobotPart {
    private static final float HALF_WIDTH = 100 / 0.8f * GameWorld.SCALE;
    private static final float HALF_HEIGHT = 100 * GameWorld.SCALE;

    public Cabin(BaseStage stage, float x, float y, float angleDegree) {
        super(new TextureMediaData(stage.getAtlas(), "part/cabin"),
                stage.getWorld().DYNAMIC_BODY_DEF.createAsRectangle(Material.PLASTIC, x, y,
                        HALF_WIDTH, HALF_HEIGHT, angleDegree));
    }

    @Override
    protected void createApertureFixtures() {
        CIRCLE_SHAPE_POSITION.set(0, -HALF_HEIGHT / 2);
        CIRCLE_SHAPE.setPosition(CIRCLE_SHAPE_POSITION);
        Fixture apertureFixture = modelData.createFixture(APERTURE_FIXTURE);
        apertureFixture.setSensor(true);
        apertureFixture.setFilterData(Collisions.APERTURE_FILTER);
    }

}
