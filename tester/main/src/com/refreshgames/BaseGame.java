package com.refreshgames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.refreshgames.controller.inputprocessor.BaseInputProcessor;

/**
 * Ancestor of game classes
 */
public abstract class BaseGame extends Game {
    @Override
    public void render() {
        InputProcessor inputProcessor = Gdx.input.getInputProcessor();
        float deltaTime = Gdx.graphics.getRawDeltaTime(); //TODO: try Gdx.graphics.getDeltaTime() as it was ?

        if (inputProcessor instanceof BaseInputProcessor) {
            BaseInputProcessor baseInputProcessor = (BaseInputProcessor) inputProcessor;
            baseInputProcessor.accelerometerChanged(
                    Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ(),
                    deltaTime
            );
        }

        //use getRawDeltaTime() instead of getDeltaTime()
        if (getScreen() != null) getScreen().render(deltaTime);
        //super.render();
    }
}
