package com.refreshgames.view.mediadata;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * data about all graphic, sounds, etс connected with one type of object
 */
public interface MediaData {
    void act(float delta);

    void draw(SpriteBatch batch);
}
