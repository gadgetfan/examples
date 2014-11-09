package com.refreshgames.other.debug;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/**
 * handles input for player
 */
public abstract class DebugListener implements EventListener {
    @Override
    public boolean handle(Event event) {
        if (!(event instanceof DebugEvent)) return false;

        return onDebugValue(((DebugEvent) event).debugValue);
    }

    protected abstract boolean onDebugValue(DebugValue debugValue);


    //collision event
    static public class DebugEvent extends Event {
        private DebugValue debugValue;

        public void setDebugValue(DebugValue debugValue) {
            this.debugValue = debugValue;
        }
    }
}
