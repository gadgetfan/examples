package com.refreshgames.model.actor.robot;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.refreshgames.model.GameWorld;
import com.refreshgames.model.actor.Part;
import com.refreshgames.model.actor.robot.PreJoint;
import com.refreshgames.model.actor.robot.finaljoint.FinalJoint;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.model.listeners.ApertureCollisionListener;
import com.refreshgames.model.modeldata.Collisions;
import com.refreshgames.model.modeldata.Material;
import com.refreshgames.model.modeldata.PartModelData;
import com.refreshgames.model.modeldata.bodydef.MaterialFixtureDef;
import com.refreshgames.model.stage.SavedActorList;
import com.refreshgames.view.mediadata.SpriteMediaData;

/**
 * part of robot, that can be connected through aperture fixtures
 */
public abstract class RobotPart extends Part implements SavedActorList.SaveAble {
    protected static final float APERTURE_RADIUS = 32 * GameWorld.SCALE;
    private static final float K_APERTURE_FIXTURE = 0.5f;
    protected static final MaterialFixtureDef APERTURE_FIXTURE = new MaterialFixtureDef();
    protected static final CircleShape CIRCLE_SHAPE = new CircleShape();
    protected static final Vector2 CIRCLE_SHAPE_POSITION = new Vector2();

    static {
        CIRCLE_SHAPE.setRadius(APERTURE_RADIUS * K_APERTURE_FIXTURE);
        APERTURE_FIXTURE.shape = CIRCLE_SHAPE;
        APERTURE_FIXTURE.setMaterial(Material.ETHER);
    }

    private boolean active = false;

    public RobotPart(SpriteMediaData mediaData, PartModelData modelData) {
        super(mediaData, modelData);
        modelData.setCollisionFilter(Collisions.ROBOT_FILTER);
        createApertureFixtures();
    }

    protected abstract void createApertureFixtures();

    @Override
    protected void createListeners() {
        super.createListeners();

        addListener(new InputListener() {
            private Vector2 localCenterDelta = new Vector2();
            private Vector2 local = new Vector2();

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                active = true;
                modelData.awakeWithConnectors();
                localCenterDelta.set(x, y).sub(modelData.getCenter());

                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                local.set(x, y).sub(localCenterDelta);
                modelData.setTransform(local);
                modelData.awakeWithConnectors();
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                active = false;
                modelData.awakeWithConnectors();
                createFinalJoint();
            }
        });

        addListener(new ApertureCollisionListener() {
            @Override
            public boolean onApertureCollision(boolean begin, Fixture activeFixture, Fixture passiveFixture) {
                PreJoint preJoint;
                if (begin) {
                    if (activeFixture.getUserData() == null && passiveFixture.getUserData() == null) {
                        preJoint = new PreJoint((BaseStage) getStage(), activeFixture, passiveFixture);
                        getStage().addActor(preJoint);
                        //DebugUtils.addDebugMessage("Added preJoint");
                    }
                } else {
                    Object activeUserData = activeFixture.getUserData();
                    Object passiveUserData = passiveFixture.getUserData();
                    if (activeUserData != null && passiveUserData != null) {
                        if (activeUserData.getClass().equals(PreJoint.class)
                                && passiveUserData.getClass().equals(PreJoint.class)
                                && activeUserData.equals(passiveUserData)) {
                            preJoint = (PreJoint) activeUserData;
                            preJoint.dispose();
                            getStage().getRoot().removeActor(preJoint);
                            //DebugUtils.addDebugMessage("Removed preJoint");
                        }
                    }
                }

                return true;
            }
        });

    }

    private void createFinalJoint() {
        //TODO: move to BeamModelData
        for (Fixture fixture : modelData.getFixtureList()) {
            if (Collisions.isApertureFixture(fixture)) {
                Object userData = fixture.getUserData();
                if (userData != null && userData.getClass().equals(PreJoint.class)) {
                    PreJoint preJoint = (PreJoint) userData;
                    getStage().addActor(new FinalJoint((BaseStage) getStage(), preJoint));
                    preJoint.dispose();
                    getStage().getRoot().removeActor(preJoint);
                    //DebugUtils.addDebugMessage("Removed preJoint and Added finalAperture");
                }
            }
        }
    }

    public boolean isActive() {
        return active;
    }
}
