package com.refreshgames.model.modeldata;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.refreshgames.model.base.scene.BaseActor;

import java.util.List;

/**
 * data about simple body
 */
public class PartModelData extends Box2dModelData {
    public static final float REVOLUTE_JOINT_ANGLE_DELTA = 1 * (float) Math.PI / 180;

    private final Body body;
    private final Material material;

    public PartModelData(Material material, Body body, float halfWidth, float halfHeight) {
        super(halfWidth, halfHeight);
        this.body = body;
        this.material = material;
    }

    @Override
    public void connectWithActor(BaseActor baseActor) {
        super.connectWithActor(baseActor); //TODO: optimization remove

        body.setUserData(baseActor);
    }

    @Override
    public Vector2 getCenter() {
        return body.getPosition();
    }

    public Material getMaterial() {
        return material;
    }

    public void setActive(boolean active) {
        body.setActive(active);
    }

    public void addTransform(Vector2 addedVector) {
        setTransform(body.getPosition().x + addedVector.x,
                body.getPosition().y + addedVector.y);
    }

    public void setTransform(float x, float y) {
        body.setTransform(x, y, body.getAngle());
    }

    public void setTransform(Vector2 worldVector) {
        body.setTransform(worldVector, body.getAngle());
    }

    public void applyLinearImpulse(Vector2 impulse) {
        body.applyLinearImpulse(impulse, body.getWorldCenter());
    }

    public void createWheelJoint(PartModelData wheel) {
        WheelJointDef wheelJointDef = new WheelJointDef();
        wheelJointDef.bodyA = body;
        wheelJointDef.bodyB = wheel.body;
        wheelJointDef.localAnchorA.set(body.getLocalPoint(wheel.body.getWorldCenter()));
        wheelJointDef.localAnchorB.set(wheel.body.getLocalCenter());
        wheelJointDef.localAxisA.set(1f, 1f);//TODO: what does localAxisA mean ?
        wheelJointDef.enableMotor = true;
        wheelJointDef.maxMotorTorque = 50000;
        wheelJointDef.motorSpeed = -6;
        wheelJointDef.frequencyHz = 10;

        body.getWorld().createJoint(wheelJointDef);
    }

    public Transform getTransform() {
        return body.getTransform();
    }

    public Vector2 getWorldPoint(Vector2 localPoint) {
        return body.getWorldPoint(localPoint);
    }

    @Override
    public void setAngleDegree(float angleDegree) {
        body.setTransform(body.getWorldCenter(), angleDegree * (float) Math.PI / 180);
    }

    @Override
    public float getAngleDegree() {
        return body.getAngle() * MathUtils.radiansToDegrees;
    }

    public void createRevoluteJoint(PartModelData addedPartBodyData, Vector2 addedPartLocalPoint, Vector2 localPoint) {
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = body;
        revoluteJointDef.bodyB = addedPartBodyData.body;
        revoluteJointDef.localAnchorA.set(localPoint);
        revoluteJointDef.localAnchorB.set(addedPartLocalPoint);
        //revoluteJointDef.lowerAngle = -REVOLUTE_JOINT_ANGLE_DELTA;
        //revoluteJointDef.upperAngle = REVOLUTE_JOINT_ANGLE_DELTA;
        revoluteJointDef.maxMotorTorque = 1000000;
        revoluteJointDef.motorSpeed = 0;
        //revoluteJointDef.enableLimit = true;
        revoluteJointDef.enableMotor = true;

        body.getWorld().createJoint(revoluteJointDef);
    }

    public void setCollisionFilter(Filter filter) {
        getCollisionFixture().setFilterData(filter);
    }

    public void makeSensor() {
        getCollisionFixture().setSensor(true);
    }

    private Fixture getCollisionFixture() {
        return body.getFixtureList().get(0);
    }

    public Vector2 toWorldPoint(Vector2 localPoint) {
        return body.getWorldPoint(localPoint);
    }

    public Fixture createFixture(FixtureDef fixture) {
        return body.createFixture(fixture);
    }


    public Vector2 calcLocalLeft() {
        return new Vector2(-getHalfWidth(), 0);
    }

    public Vector2 calcLocalRight() {
        return new Vector2(getHalfWidth(), 0);
    }

    public Vector2 calcLocalLeft(float fromEdge) {
        return new Vector2(-getHalfWidth() + fromEdge, 0);
    }

    public Vector2 calcLocalRight(float fromEdge) {
        return new Vector2(getHalfWidth() - fromEdge, 0);
    }

    public void connect(PartModelData addedPartModelData, Vector2 addedPartLocalPoint,
                        Vector2 localPoint) {
        addedPartModelData.setTransform(toWorldPoint(localPoint)
                .sub(addedPartModelData.toWorldPoint(addedPartLocalPoint)));
    }

    public List<Fixture> getFixtureList() {
        return body.getFixtureList();
    }

    public void awakeWithConnectors() {
        body.setAwake(true);
        for (JointEdge joint : body.getJointList()) {
            joint.other.setAwake(true);
        }
    }

    public void enableGravity() {
        body.setGravityScale(1);
    }

    public void disableGravity() {
        body.setGravityScale(0);
    }
}
