package com.refreshgames.model.listeners;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.refreshgames.model.base.scene.BaseActor;

/**
 * handles collisions between actors
 */
public abstract class CollisionListener implements EventListener {
    @Override
    public boolean handle(Event event) {
        if (!(event instanceof CollisionEvent)) return false;

        CollisionEvent collisionEvent = (CollisionEvent) event;

        return onCollision(collisionEvent.collidedActor, collisionEvent.impulse);
    }

    public abstract boolean onCollision(BaseActor collidedActor, float impulse);

    //collision event
    static public class CollisionEvent extends NotBubblesEvent {
        private BaseActor collidedActor;
        private float impulse;

        public void setCollidedActor(BaseActor collidedActor) {
            this.collidedActor = collidedActor;
        }

        public void setImpulse(float impulse) {
            this.impulse = impulse;
        }
    }
}
