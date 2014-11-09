package com.refreshgames.model.base.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.refreshgames.controller.WorldController;
import com.refreshgames.controller.inputprocessor.BaseInputProcessor;
import com.refreshgames.model.actor.Part;
import com.refreshgames.model.base.world.BaseWorld;
import com.refreshgames.other.debug.DebugUtils;
import com.refreshgames.view.assets.Assets;
import com.refreshgames.view.mediadata.TextDebugMediaData;

public abstract class BaseStage extends Stage {
    public static int CONTINUE_LIFE_CYCLE = 0;

    private TextureAtlas atlas;
    private BaseWorld world;
    private WorldController controller;

    protected Part player;

    private TextDebugMediaData textDebugMediaData;
    public int lifeCycleCode = CONTINUE_LIFE_CYCLE;

    public BaseStage(float width, float height, TextureAtlas atlas, BaseInputProcessor inputProcessor) {
        super(width, height, false);
        this.atlas = atlas;
        this.world = generateWorld();
        createActors();
        createListeners();

        controller = new WorldController(this);
        inputProcessor.setController(controller);

        if (DebugUtils.TEXT_DEBUG) {
            textDebugMediaData = new TextDebugMediaData(800, 480);
        }

    }

    protected abstract void createActors();

    protected abstract void createListeners();

    protected abstract BaseWorld generateWorld();

    public Part getPlayer() {
        return player;
    }

    @Override
    public void act(float deltaTime) {
        world.act(deltaTime); //TODO: make constant steps with help of World.clearForces (see description)
        Assets.playSounds();

        controller.processInput(deltaTime);

        super.act(deltaTime);


        if (DebugUtils.DEBUG_DETECTOR) {
            Gdx.app.debug("debug", "deltaTime = " + deltaTime);
        }
    }

    @Override
    public void draw() {
        setupCamera();
        setupControls();
        super.draw();
        if (DebugUtils.DEBUG_BOX2D_RENDERING) {
            world.render(getCamera().combined);
        }
        if (DebugUtils.TEXT_DEBUG) {
            textDebugMediaData.draw();
        }
    }

    protected abstract void setupControls();

    protected abstract void setupCamera();

    @Override
    public void dispose() {
        for (Actor actor : getActors()) {
            if (actor instanceof Disposable) {
                ((Disposable)actor).dispose();
            }
        }

        if (world != null) {
            world.dispose();
        }

        super.dispose();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public BaseWorld getWorld() {
        return world;
    }

    public Part calcHitPart(float x, float y) {
        return world.calcOverlapped(x, y);
    }

    public int getLifeCycleCode() {
        return lifeCycleCode;
    }

    public void setLifeCycleCode(int lifeCycleCode) {
        this.lifeCycleCode = lifeCycleCode;
    }
}
