package com.refreshgames.model.actor.robot;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.refreshgames.model.GameWorld;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.model.modeldata.Collisions;
import com.refreshgames.model.modeldata.Material;
import com.refreshgames.model.modeldata.PartModelData;
import com.refreshgames.view.mediadata.BeamTextureMediaData;

/**
 * Beam to construct all
 */
public abstract class Beam extends RobotPart {
    /*
    _________________________________________________________________________________________________________
    |                                                                                                        |
    |<--BEFORE_APERTURE-->[2 * APERTURE_RADIUS]<--INTER_APERTURE-->[2 * APERTURE_RADIUS]<--BEFORE_APERTURE-->|
    |________________________________________________________________________________________________________|
     */
    public static final float HALF_HEIGHT = 50 * GameWorld.SCALE;
    private static final float BEFORE_APERTURE = 18 * GameWorld.SCALE;
    private static final float INTER_APERTURE = 36 * GameWorld.SCALE;

    private static final float BEAM_CONNECTION_OVERLAP = 0f;
    private static final Material MATERIAL = Material.PLASTIC;

    protected Beam(BaseStage stage, PartModelData bodyData, int apertureCnt) {
        super(new BeamTextureMediaData(stage.getAtlas(), apertureCnt), bodyData);
    }

    public Beam(BaseStage stage, float x, float y, float angleDegree, int apertureCnt) {
        this(stage, stage.getWorld().DYNAMIC_BODY_DEF.createAsRectangle(
                MATERIAL, x, y, calcHalfWidth(apertureCnt), HALF_HEIGHT, angleDegree), apertureCnt);
    }

    //TODO: move to BeamModelData
    @Override
    protected void createApertureFixtures() {
        for (float xAperture = -modelData.getHalfWidth() + BEFORE_APERTURE + APERTURE_RADIUS;
             xAperture < modelData.getHalfWidth(); xAperture += INTER_APERTURE + 2 * APERTURE_RADIUS) {
            CIRCLE_SHAPE_POSITION.set(xAperture, 0);
            CIRCLE_SHAPE.setPosition(CIRCLE_SHAPE_POSITION);
            Fixture apertureFixture = modelData.createFixture(APERTURE_FIXTURE);
            apertureFixture.setSensor(true);
            apertureFixture.setFilterData(Collisions.APERTURE_FILTER);
        }
    }

    public void addBeam(Beam beam, float angleDegree) {
        Vector2 localPoint = modelData.calcLocalLeft(BEAM_CONNECTION_OVERLAP);

        beam.modelData.setAngleDegree(modelData.getAngleDegree() - (180 - angleDegree));
        Vector2 addedBeamLocalPoint = beam.modelData.calcLocalRight(BEAM_CONNECTION_OVERLAP);

        modelData.connect(beam.getModelData(), addedBeamLocalPoint, localPoint);
        modelData.createRevoluteJoint(beam.modelData, addedBeamLocalPoint, localPoint);
    }

    //TODO: move to BeamModelData
    private static float calcHalfWidth(int apertureCnt) {
        return BEFORE_APERTURE + apertureCnt * APERTURE_RADIUS + (apertureCnt - 1) * INTER_APERTURE / 2;
    }
}
