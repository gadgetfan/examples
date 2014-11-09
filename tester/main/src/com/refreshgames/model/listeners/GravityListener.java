package com.refreshgames.model.listeners;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/**
 * handles input for player
 */
public abstract class GravityListener implements EventListener {
    @Override
    public boolean handle(Event event) {
        if (!(event instanceof GravityEvent)) return false;

        GravityEvent gravityEvent = (GravityEvent) event;
        return onGravity(gravityEvent.gravity);
    }

    protected abstract boolean onGravity(Vector2 gravity);

    //collision event
    static public class GravityEvent extends NotBubblesEvent {
        private Vector2 gravity;

        public void setGravity(Vector2 gravity) {
            this.gravity = gravity;
        }
    }
}
