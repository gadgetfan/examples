package com.refreshgames.model.modeldata;

import com.badlogic.gdx.physics.box2d.Joint;

/**
 * data about simple body
 */
public abstract class JointModelData<J extends Joint> extends Box2dModelData { //TODO: optimization remove
    protected J joint;

    public JointModelData(J joint, float halfWidth, float halfHeight) {
        super(halfWidth, halfHeight);

        this.joint = joint;
    }
}
