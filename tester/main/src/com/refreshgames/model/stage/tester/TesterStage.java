package com.refreshgames.model.stage.tester;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.refreshgames.GameState;
import com.refreshgames.controller.inputprocessor.BaseInputProcessor;
import com.refreshgames.model.GameWorld;
import com.refreshgames.model.actor.Background;
import com.refreshgames.model.actor.controls.EnergyIndicator;
import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.base.world.BaseWorld;
import com.refreshgames.model.listeners.AccelerometerListener;
import com.refreshgames.model.listeners.EnergyConsumptionListener;
import com.refreshgames.model.stage.GameStage;
import com.refreshgames.model.stage.constructor.ConstructorStage;

import java.util.List;

/**
 * Stage to test robot (for child)
 */
public class TesterStage extends GameStage {
    public static final int LEVEL_FINISHED_EXIT = 1;

    private EnergyIndicator energyIndicator;

    private final Vector2 jointsCenter = new Vector2();

    public TesterStage(float width, float height, TextureAtlas atlas, BaseInputProcessor processor) {
        super(width, height, atlas, processor);

        getWorld().setGravity(GameWorld.NORMAL_GRAVITY);
    }

    /*@Override
    public void act(float deltaTime) {
        //$$
        if (GameState.currentLevel == 1) {
            super.act(deltaTime);    //TODO: optimisation remove
        }
    }*/

    @Override
    protected void createActors() {
        super.createActors();

        addConstantActors();
        loadSavedActors();
        sortActors(); //to sort new created FinalJoint right
    }

    private void loadSavedActors() {
        ConstructorStage constructorStage = GameState.CONSTRUCTOR_SCREEN.getStage();
        List<BaseActor> worldActorList = constructorStage.fetchWorldActorList();

        for (BaseActor actor : worldActorList) {
            addActor(actor);
        }

        player = constructorStage.getPlayer();
        borders = constructorStage.getBorders();

        //SavedActorList.loadFromStage(this).saveTo("save.dsc");
    }

    private void addConstantActors() {
        addActor(new Background(this));

        energyIndicator = new EnergyIndicator(this, 5);
        addActor(energyIndicator);

    }

    @Override
    protected void setupControls() {
        super.setupControls();

        energyIndicator.setPosition(getCamera().position.x - getWidth() / 2,
                getCamera().position.y - getHeight() / 2);
    }

    @Override
    protected void createListeners() {
        addListener(new TesterStageInputListener(this));
        addListener(new AccelerometerListener() {
            @Override
            protected boolean onJump(float jumpVelocity) {
                energyIndicator.addEnergy(jumpVelocity * jumpVelocity);

                return true;
            }
        });
        addListener(new EnergyConsumptionListener() {
            @Override
            protected boolean onEnergyConsumption(float energy) {
                return energyIndicator.removeEnergy(energy);
            }
        });
    }


    @Override
    protected void setupCamera() {
        ((GameWorld)getWorld()).calcJointsCenter(jointsCenter);  //TODO: remove  (GameWorld) ?
        getCamera().position.x = jointsCenter.x;
        getCamera().position.y = jointsCenter.y;
    }

    @Override
    protected BaseWorld generateWorld() {
        return GameState.CONSTRUCTOR_SCREEN.getStage().getWorld();
    }
}
