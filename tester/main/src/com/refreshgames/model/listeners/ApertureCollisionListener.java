package com.refreshgames.model.listeners;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/**
 * handles collisions between actors
 */
public abstract class ApertureCollisionListener implements EventListener {
    @Override
    public boolean handle(Event event) {
        if (!(event instanceof ApertureCollisionEvent)) return false;

        ApertureCollisionEvent apertureCollisionEvent = (ApertureCollisionEvent) event;

        return onApertureCollision(apertureCollisionEvent.begin, apertureCollisionEvent.activeFixture,
                apertureCollisionEvent.passiveFixture);
    }

    public abstract boolean onApertureCollision(boolean begin, Fixture activeFixture, Fixture passiveFixture);

    //collision event
    static public class ApertureCollisionEvent extends NotBubblesEvent {
        /**
         * Fixture of circle inside aperture belonged to bode, that user drugs
         */
        private Fixture activeFixture;
        /**
         * Fixture of circle inside aperture belonged to bode, that user NOT drugs. It lies free.
         */
        private Fixture passiveFixture;

        /**
         * is this begin on end of collision
         */
        private boolean begin;

        public void setActiveFixture(Fixture activeFixture) {
            this.activeFixture = activeFixture;
        }

        public void setPassiveFixture(Fixture passiveFixture) {
            this.passiveFixture = passiveFixture;
        }

        public void setBegin(boolean begin) {
            this.begin = begin;
        }
    }
}
