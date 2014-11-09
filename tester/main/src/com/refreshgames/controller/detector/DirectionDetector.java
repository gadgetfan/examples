package com.refreshgames.controller.detector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.refreshgames.other.debug.DebugValueSender;

/**
 * Detector of moving direction
 */
public class DirectionDetector {
    private static final float MIN_PROJECTION_LENGTH = 0.1f;

    public static enum State {NONE, UP, DOWN}

    //debug
    private DebugValueSender debugProjection = new DebugValueSender(20, new Color(1, 0, 0, 1));

    private State state;
    private Vector3 gVector;
    float gVectorLength;
    private Vector3 acceleration;
    private Vector3 velocity;

    public DirectionDetector() {
        velocity = new Vector3();
        acceleration = new Vector3();
    }

    public void init(float x, float y, float z) {
        gVector = new Vector3(x, y, z);
        gVectorLength = gVector.len();
        if (gVectorLength <= 0) {
            throw new RuntimeException("Incorrect DirectionDetector initialization");
        }
        velocity.set(0, 0, 0);
    }

    public void setAccelerationData(float x, float y, float z, float deltaTime) {
        acceleration.set(x, y, z);
        acceleration.sub(gVector).scale(deltaTime, deltaTime, deltaTime);
        velocity.add(acceleration);
        calcState();
    }

    public State getState() {
        return state;
    }

    private void calcState() {
        float projection = calcProjection();
        debugProjection.sendValue(projection);
        if (Math.abs(projection) < MIN_PROJECTION_LENGTH) {
            state = State.NONE;
        } else if (projection > 0) {
            state = State.UP;
        } else {
            state = State.DOWN;
        }
    }

    private float calcProjection() {
        return velocity.dot(gVector) / gVectorLength;
    }
}
