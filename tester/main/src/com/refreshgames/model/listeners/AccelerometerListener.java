package com.refreshgames.model.listeners;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/**
 * handles input from accelerometer
 */
public abstract class AccelerometerListener implements EventListener {
    @Override
    public boolean handle(Event event) {
        if (!(event instanceof AccelerometerEvent)) return false;

        AccelerometerEvent accelerometerEvent = (AccelerometerEvent) event;
        switch (accelerometerEvent.state) {
            case JUMP:
                return onJump(accelerometerEvent.value);
            default:
                return false;
        }
    }

    protected abstract boolean onJump(float impulse);

    //collision event
    static public class AccelerometerEvent extends NotBubblesEvent {
        private EventState state;
        private float value;

        public void setState(EventState state) {
            this.state = state;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }

    static public enum EventState {JUMP} //TODO: optimization remove
}
