package com.refreshgames.model.actor.robot;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.refreshgames.model.GameWorld;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.model.modeldata.Collisions;
import com.refreshgames.model.modeldata.Material;
import com.refreshgames.view.mediadata.TextureMediaData;

/**
 * Beam to construct all
 */
public class Wheel extends RobotPart {
    public static final float RADIUS = 150 * GameWorld.SCALE;

    public Wheel(BaseStage stage, float x, float y, float angleDegree) {
        //TODO: use animation to rotate wheel, do not rotate texture
        super(new TextureMediaData(stage.getAtlas(), "part/wheel"),
                stage.getWorld().DYNAMIC_BODY_DEF.createAsCircle(Material.RUBBER_WITH_AIR,
                        x, y, RADIUS, angleDegree));
    }

    @Override
    protected void createApertureFixtures() {
        CIRCLE_SHAPE_POSITION.set(0, 0);
        CIRCLE_SHAPE.setPosition(CIRCLE_SHAPE_POSITION);
        Fixture apertureFixture = modelData.createFixture(APERTURE_FIXTURE);
        apertureFixture.setSensor(true);
        apertureFixture.setFilterData(Collisions.APERTURE_FILTER);
    }

}
