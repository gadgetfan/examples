package com.refreshgames.model.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.refreshgames.controller.inputprocessor.AndroidInputProcessor;
import com.refreshgames.controller.inputprocessor.BaseInputProcessor;
import com.refreshgames.controller.inputprocessor.DesktopInputProcessor;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.view.assets.Assets;

/**
 * ancestor of all screens
 */
public abstract class BaseScreen<S extends BaseStage> implements Screen {
    protected BaseInputProcessor inputProcessor;
    protected S stage;
    //private OrthographicCamera guiCamera; to draw GUI: strings and other

    /**
     * called only once by creating stage
     *
     * @param width
     * @param height
     * @return
     */
    protected abstract S createStage(int width, int height);

    /**
     * called if stage returned lifeCycleCode not CONTINUE_LIFE_CYCLE
     *
     * @param lifeCycleCode
     */
    protected abstract void processStageExit(int lifeCycleCode);


    public BaseScreen() { //TODO: move InputProcessor to assets
        switch (Gdx.app.getType()) {
            case Android:
                inputProcessor = new AndroidInputProcessor();
                break;
            case Desktop:
                inputProcessor = new DesktopInputProcessor();
                break;
            default:
                throw new IllegalStateException("Incorrect application type");
        }
    }

    @Override
    public void show() {
    }

    /**
     * is called after setScreen()
     *
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        //TODO: optimization remove first call, don't create stage. resize is called twice at least

        if (stage != null) {
            disposeStage();
        }

        Gdx.input.setInputProcessor(inputProcessor);
        Assets.reloadTextures(); //TODO: why I should do this ? If not atlas lose texture data after pause
        stage = createStage(width, height);
    }

    protected void disposeStage() {
        stage.dispose();
        stage = null;
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        if (stage != null) {
            disposeStage();
        }
        Gdx.input.setInputProcessor(null);
    }


    @Override
    public void render(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();

        int lifeCycleCode = stage.getLifeCycleCode();
        if (lifeCycleCode != BaseStage.CONTINUE_LIFE_CYCLE) {
            processStageExit(lifeCycleCode);
        }
    }

    public S getStage() {
        return stage;
    }
}
