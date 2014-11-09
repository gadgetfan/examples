package com.refreshgames.model.stage;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.refreshgames.controller.inputprocessor.BaseInputProcessor;
import com.refreshgames.model.actor.Background;
import com.refreshgames.model.actor.Borders;
import com.refreshgames.model.base.scene.BaseStage;

import java.util.Random;

/**
 * ancestor of ConstructorStage and TesterStage
 */
public abstract class GameStage extends BaseStage implements SavedActorList.SaveAble { //TODO: optimization remove
    //camera
    public static final float FRUSTUM_HEIGHT = 10;

    private static final float UNDO_BUTTON_WIDTH = 1.5f;
    public static final int UNDO_BUTTON_CLICKED_EXIT = 100;

    private static final Random RANDOM = new Random();
    private static final int SEGMENT_CNT = 100;

    protected Borders borders;

    protected Button undoButton;

    public GameStage(float width, float height, TextureAtlas atlas, BaseInputProcessor processor) {
        super(width * FRUSTUM_HEIGHT / height, FRUSTUM_HEIGHT, atlas, processor);
        //Assets.playMusic(Assets.FOREST);
    }

    @Override
    protected void createActors() {
        addActor(new Background(this));

        undoButton = new Button(new TextureRegionDrawable(getAtlas().findRegion("control/undo")));
        undoButton.setWidth(UNDO_BUTTON_WIDTH);
        undoButton.setHeight(UNDO_BUTTON_WIDTH
                * undoButton.getStyle().up.getMinHeight() / undoButton.getStyle().up.getMinWidth());
        undoButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                lifeCycleCode = UNDO_BUTTON_CLICKED_EXIT;

                return true;
            }
        });
        addActor(undoButton);

    }

    @Override
    protected void setupControls() {
        //right top
        undoButton.setPosition(getCamera().position.x + getWidth() / 2 - undoButton.getWidth(),
                getCamera().position.y + getHeight() / 2 - undoButton.getHeight());
    }

    public Borders getBorders() {
        return borders;
    }

    public void sortActors() {
        getActors().sort(ActorsComparator.Z_INDEX_ACTOR_COMPARATOR);
    }

    /*protected Borders generateBorders() {
        Borders borders = new Borders();
        for (int i = 0; i < SEGMENT_CNT; ++i) {
            int r = RANDOM.nextInt(100);

            if (r < 40) borders.addSegment(Borders.Type.FLAT);
            else if (r < 60) borders.addSegment(Borders.Type.UP);
            else if (r < 80) borders.addSegment(Borders.Type.DOWN);
            else if (r < 90) borders.addSegment(Borders.Type.PIT);
            else if (r < 100) borders.addSegment(Borders.Type.PEAK);
        }
        borders.create(this);

        return borders;
    }*/
}
