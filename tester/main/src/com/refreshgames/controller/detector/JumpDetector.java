package com.refreshgames.controller.detector;

import com.badlogic.gdx.Gdx;

/**
 * Finite state machine. Gets accelerometer data and detects if jump took place and initial jump velocity
 */
public class JumpDetector {
    private static final float JUMP_READY_INTERVAL = .1f;
    private static final float MIN_ACCELERATION_INTERVAL = .1f;
    static final float TOP_ACCELERATION_BOUND = 10;
    static final float BOTTOM_ACCELERATION_BOUND = 5;
    private static final String TAG = "JumpDetector";
    private static final float ACCELERATION_HELPER = 3;


    private enum State {BETWEEN, ACCELERATION, JUMP_READY}

    private State state;
    private AccelerometerData signal;
    private float initialVelocity;
    private float stateTime;

    public JumpDetector() {
        signal = new AccelerometerData();
    }

    public void init() {
        changeState(State.BETWEEN);
    }

    public void setNextAccelerometerData(float x, float y, float z) {
        signal.setNextAccelerometerData(x, y, z);
    }

    public float getJumpVelocity(float deltaTime) {
        boolean isJump = false;
        stateTime += deltaTime;
        Gdx.app.log(TAG, "acceleration: " + signal.getAcceleration() + " initialVelocity = " + initialVelocity
                + " deltaTime = " + deltaTime);
        float jumpVelocity = 0;
        switch (state) {
            case BETWEEN:
                if (signal.isIncrease() && isGreaterThanTop()) {
                    changeState(State.ACCELERATION);
                }
                break;

            case ACCELERATION:
                accelerate(signal.getAcceleration(), deltaTime);
                if (!isGreaterThanTop()) {
                    if (isNormalAccelerationLength()) {
                        changeState(State.JUMP_READY);
                    } else {
                        changeState(State.BETWEEN);
                    }
                }
                break;

            case JUMP_READY:
/*
                if (signal.isGreaterThanTop()) {
                    accelerate(signal.getAcceleration(), deltaTime);
                }

*/
                isJump = isLessThanBottom();
                if (isJumpReadyTimeOver() || isJump) {
                    if (isJump) {
                        Gdx.app.log(TAG, "");
                        Gdx.app.log(TAG, "JUMP!!! velocity " + initialVelocity);
                        Gdx.app.log(TAG, "");
                        jumpVelocity = initialVelocity;
                    }
                    changeState(State.BETWEEN);
                }
                break;

            default:
                throw new RuntimeException("Incorrect JumpDetector state"); //TODO: optimization
        }

        if (isJump) {
            return jumpVelocity;
        } else {
            return 0;
        }
    }

    private boolean isNormalAccelerationLength() {
        return (stateTime > MIN_ACCELERATION_INTERVAL);
    }

    private void accelerate(float acceleration, float deltaTime) {
        initialVelocity += (acceleration - TOP_ACCELERATION_BOUND) * JumpDetector.ACCELERATION_HELPER * deltaTime;
    }

    private boolean isJumpReadyTimeOver() {
        return stateTime > JUMP_READY_INTERVAL;
    }

    private void changeState(State newState) {
        Gdx.app.log(TAG, "State changed from " + state + "to " + newState);
        state = newState;
        if (newState == State.BETWEEN) {
            initialVelocity = 0;
        }
        stateTime = 0;
    }

    public boolean isGreaterThanTop() {
        return (signal.getAcceleration() > JumpDetector.TOP_ACCELERATION_BOUND);
    }

    public boolean isLessThanBottom() {
        return (signal.getAcceleration() < JumpDetector.BOTTOM_ACCELERATION_BOUND);
    }

}
