package com.refreshgames.model.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.refreshgames.controller.inputprocessor.BaseInputProcessor;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.model.base.world.BaseWorld;

public class GameOverStage extends BaseStage {
    public GameOverStage(float width, float height, TextureAtlas atlas, BaseInputProcessor inputProcessor) {
        super(width, height, atlas, inputProcessor);
    }

    @Override
    protected void createActors() {
        Label gameOverLabel = new Label("GAME OVER", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        gameOverLabel.setPosition(getWidth() / 2 - gameOverLabel.getWidth() / 2, getHeight() / 2);
        addActor(gameOverLabel);
    }

    @Override
    public void act(float deltaTime) {
        //no actions
    }

    @Override
    protected void createListeners() {
    }

    @Override
    protected BaseWorld generateWorld() {
        return null;
    }

    @Override
    protected void setupControls() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void setupCamera() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
