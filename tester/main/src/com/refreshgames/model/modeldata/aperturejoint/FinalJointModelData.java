package com.refreshgames.model.modeldata.aperturejoint;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.refreshgames.model.actor.Part;
import com.refreshgames.model.modeldata.JointModelData;
import com.refreshgames.model.stage.ActorsComparator;
import com.refreshgames.model.stage.SavedActorList;

/**
 * Final joint
 */
public class FinalJointModelData extends JointModelData<RevoluteJoint> {
    private static final float ABS_MOTOR_SPEED = 2;
    private static final float MOTOR_TORQUE_FORCE = 100000000;

    private final Transform transform = new Transform();

    public FinalJointModelData(PreJointModelData preJointModelData) {
        super(null, preJointModelData.getHalfWidth(), preJointModelData.getHalfHeight());

        Body activeBody = preJointModelData.activeFixture.getBody();
        Body passiveBody = preJointModelData.passiveFixture.getBody();
        Vector2 activePosition = activeBody.getWorldPoint(((CircleShape) preJointModelData.activeFixture.getShape())
                .getPosition());
        Vector2 passivePosition = passiveBody.getWorldPoint(((CircleShape) preJointModelData.passiveFixture.getShape())
                .getPosition());
        Vector2 transfer = new Vector2(passivePosition.x - activePosition.x, passivePosition.y - activePosition.y);
        ((Part) activeBody.getUserData()).getModelData().addTransform(transfer);


        Body wheel;
        Body basement;
        if (ActorsComparator.Z_INDEX_SAVEABLE_COMPARATOR.compare((SavedActorList.SaveAble)activeBody.getUserData(),
                (SavedActorList.SaveAble)passiveBody.getUserData()) >= 0) {
            wheel = activeBody;
            basement = passiveBody;
        } else {
            wheel = passiveBody;
            basement = activeBody;
        }
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = basement;
        revoluteJointDef.bodyB = wheel;
        revoluteJointDef.localAnchorA.set(basement.getLocalPoint(preJointModelData.getCenter()));
        revoluteJointDef.localAnchorB.set(wheel.getLocalPoint(preJointModelData.getCenter()));

        revoluteJointDef.enableMotor = true;

        joint = (RevoluteJoint) basement.getWorld().createJoint(revoluteJointDef);
        //$$disableMotor();
        enableMotor();//$$
    }

    @Override
    public Vector2 getCenter() {
        return joint.getAnchorA();
    }

    @Override
    public Transform getTransform() {
        transform.setPosition(getCenter());
        transform.setRotation(joint.getJointAngle());

        return transform;
    }

    @Override
    public float getAngleDegree() {
        return MathUtils.radiansToDegrees * joint.getJointAngle();
    }

    @Override
    public void setAngleDegree(float rotation) {
        throw new IllegalStateException("ApertureJointModelData: cannot change angle after creating final joint");
    }

    public boolean hit(float x, float y) {
        Vector2 center = getCenter();
        return ((center.x - halfWidth) <= x && (center.x + halfWidth) >= x
                && (center.y - halfHeight) <= y && (center.y + halfWidth) >= y);
    }

    public void stopMotor() {
        joint.setMotorSpeed(0);
    }

    public void startMotor(boolean clock) {
        joint.setMotorSpeed(clock ? -ABS_MOTOR_SPEED : ABS_MOTOR_SPEED);
    }

    public boolean isMotorRunning() {
        return (joint.getMotorSpeed() != 0);
    }

    public void disableMotor() {
        joint.setMaxMotorTorque(0);
    }

    public void enableMotor() {
        joint.setMaxMotorTorque(MOTOR_TORQUE_FORCE);
    }
}
