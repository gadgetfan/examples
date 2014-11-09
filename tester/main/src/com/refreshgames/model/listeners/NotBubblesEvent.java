package com.refreshgames.model.listeners;

import com.badlogic.gdx.scenes.scene2d.Event;

/**
 * Event, that doesn't bubble
 */
public class NotBubblesEvent extends Event {
    public NotBubblesEvent() {
        setBubbles(false);
    }
}
