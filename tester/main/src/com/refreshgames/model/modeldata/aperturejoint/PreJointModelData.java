package com.refreshgames.model.modeldata.aperturejoint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Transform;
import com.refreshgames.model.GameWorld;
import com.refreshgames.model.modeldata.Box2dModelData;

/**
 * Temporal joint, is showed when user still touch connected part
 */
public class PreJointModelData extends Box2dModelData {
    public static final float RADIUS = 40 * GameWorld.SCALE;

    private final Transform transform = new Transform();
    private Vector2 center = new Vector2();

    Fixture activeFixture;
    Fixture passiveFixture;

    public PreJointModelData(Fixture activeFixture, Fixture passiveFixture) {
        super(RADIUS, RADIUS);

        this.activeFixture = activeFixture;
        this.passiveFixture = passiveFixture;
        this.center.set(passiveFixture.getBody().getWorldPoint(((CircleShape)
                passiveFixture.getShape()).getPosition())); //TODO: why I need .sub(halfWidth, halfHeight) ?
        this.transform.setPosition(getCenter());
        this.transform.setRotation(0);
    }

    @Override
    public Vector2 getCenter() {
        return center;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public float getAngleDegree() {
        return 0;
    }

    @Override
    public void setAngleDegree(float angleDegree) {
    }

    public void dispose() {
        activeFixture.setUserData(null);
        passiveFixture.setUserData(null);
    }

    public Fixture getActiveFixture() {
        return activeFixture;
    }

    public Fixture getPassiveFixture() {
        return passiveFixture;
    }
}
