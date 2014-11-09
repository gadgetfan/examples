package com.refreshgames.controller.detector;

/**
 * Detector of squats
 */
public class SquatDetector {
    public static enum State {NONE, TOP, BOTTOM}

    private DirectionDetector directionDetector;
    private State state;
    private DirectionDetector.State oldDdState;

    public SquatDetector() {
        this.directionDetector = new DirectionDetector();
        this.state = State.NONE;
        this.oldDdState = DirectionDetector.State.NONE;
    }

    public void init(float x, float y, float z) {
        directionDetector.init(x, y, z);
    }

    public void setAccelerationData(float x, float y, float z, float deltaTime) {
        directionDetector.setAccelerationData(x, y, z, deltaTime);

        state = State.NONE;
        switch (directionDetector.getState()) {
            case UP:
                if (oldDdState.equals(DirectionDetector.State.DOWN)) {
                    state = State.BOTTOM;
                }
                oldDdState = DirectionDetector.State.UP;
                break;
            case DOWN:
                if (oldDdState.equals(DirectionDetector.State.UP)) {
                    state = State.TOP;
                }
                oldDdState = DirectionDetector.State.DOWN;
                break;
            case NONE:
            default:
                break;
        }
        ;
    }

    public State getState() {
        return state;
    }
}
