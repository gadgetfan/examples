package com.refreshgames.model.stage.constructor;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.refreshgames.GameState;
import com.refreshgames.controller.inputprocessor.BaseInputProcessor;
import com.refreshgames.model.GameWorld;
import com.refreshgames.model.actor.Background;
import com.refreshgames.model.actor.Borders;
import com.refreshgames.model.actor.Part;
import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.base.world.BaseWorld;
import com.refreshgames.model.modeldata.Box2dModelData;
import com.refreshgames.model.stage.GameStage;
import com.refreshgames.model.stage.SavedActorList;

import java.util.ArrayList;
import java.util.List;

/**
 * Stage to construct a robot
 */
public class ConstructorStage extends GameStage {
    public static final int START_BUTTON_CLICKED_EXIT = 1;
    private static final float START_BUTTON_WIDTH = 3;

    private Button startButton;
    private ArrayList<BaseActor> worldActorList = new ArrayList<BaseActor>();

    public ConstructorStage(float width, float height, TextureAtlas atlas, BaseInputProcessor processor) {
        super(width, height, atlas, processor);

        getWorld().setGravity(GameWorld.ZERO_GRAVITY);
    }

    @Override
    protected void createActors() {
        super.createActors();

        addConstantActors();
        loadSavedActors(String.format("levels/level%d.dsc", GameState.getCurrentLevel()));
    }

    public List<BaseActor> fetchWorldActorList() {
        worldActorList.clear();
        for (Actor actor : getActors()) {
            if (actor instanceof BaseActor) {
                BaseActor baseActor = (BaseActor) actor;
                if (baseActor.getModelData() instanceof Box2dModelData) {
                    worldActorList.add((BaseActor) actor);
                }
            }
        }

        for (Actor actor : worldActorList) {
            getRoot().removeActor(actor);
        }

        return worldActorList;
    }

    private void addConstantActors() {
        startButton = new Button(new TextureRegionDrawable(getAtlas().findRegion("control/start")));
        startButton.setWidth(START_BUTTON_WIDTH);
        startButton.setHeight(START_BUTTON_WIDTH
                * startButton.getStyle().up.getMinHeight() / startButton.getStyle().up.getMinWidth());
        startButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                lifeCycleCode = START_BUTTON_CLICKED_EXIT;

                return true;
            }
        });
        addActor(startButton);

    }

    @Override
    protected void setupControls() {
        super.setupControls();

        //left top
        startButton.setPosition(getCamera().position.x - getWidth() / 2,
                getCamera().position.y + getHeight() / 2 - startButton.getHeight());
    }

    private void loadSavedActors(String saveName) {
        SavedActorList savedActorList = SavedActorList.loadFromFile(saveName, this);

        //**SavedActorList savedActorList = new SavedActorList();
        /*Borders borders = generateBorders();
        savedActorList.addActor(borders);
        savedActorList.addActor(new Finish(this, borders.getLastPoint().x,
                borders.getLastPoint().y + Finish.HALF_HEIGHT));

        savedActorList.addPlayer(new Cabin(this, 0, 3));

        savedActorList.addActor(new Beam5(this, 5, 3));
        savedActorList.addActor(new Beam5(this, -5, 3));
        savedActorList.addActor(new Wheel(this, 5, 6));
        savedActorList.addActor(new Wheel(this, 7, 6));
        savedActorList.addActor(new Beam5(this, 0, 0 + Beam.HALF_HEIGHT));

        savedActorList.saveTo("stage1.dsc");*/

        for (SavedActorList.SaveAble actor : savedActorList.getActorList()) {
            addActor((Actor) actor);
        }

        player = (Part) savedActorList.getPlayer();
        borders = (Borders) savedActorList.getBorders();
    }

    @Override
    protected void createListeners() {
        Rectangle cameraBounds = new Rectangle(borders.getBounds().x + getWidth() / 2,
                borders.getBounds().y + getHeight() / 2,
                borders.getBounds().width - getWidth(),
                borders.getBounds().height);
        getCamera().position.x = borders.getFirstPoint().x + getWidth() / 2;
        getCamera().position.y = borders.getFirstPoint().y + getHeight() / 2;

        addListener(new ConstructorStageInputListener(this, cameraBounds));

    }

    @Override
    protected void setupCamera() {
        //camera position is set in ConstructorStageInputListener
    }

    @Override
    protected BaseWorld generateWorld() { //TODO: optimize use one world! Don't recreate in on every stage
        return new GameWorld(GameWorld.ZERO_GRAVITY);
    }

}
