package com.refreshgames.controller.detector;

/**
 * data from accelerometer
 */
public class AccelerometerData {
    private float acceleration;
    private boolean accelerationIncreased;

    public void setNextAccelerometerData(float x, float y, float z) {
        float accelerationOld = acceleration;
        setAcceleration(x, y, z);
        accelerationIncreased = (acceleration > accelerationOld);
    }

    private void setAcceleration(float x, float y, float z) {
        acceleration = (float) Math.sqrt(x * x + y * y + z * z);
    }

    public boolean isIncrease() {
        return accelerationIncreased;
    }

    public float getAcceleration() {
        return acceleration;
    }
}
