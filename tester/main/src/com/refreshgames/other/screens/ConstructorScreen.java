package com.refreshgames.other.screens;

import com.refreshgames.GameState;
import com.refreshgames.model.base.BaseScreen;
import com.refreshgames.model.stage.GameStage;
import com.refreshgames.model.stage.constructor.ConstructorStage;
import com.refreshgames.view.assets.Assets;

/**
 * Screen to construct robot
 */
public class ConstructorScreen extends BaseScreen<ConstructorStage> {
    @Override
    protected ConstructorStage createStage(int width, int height) {
        return new ConstructorStage(width, height, Assets.textureAtlas, inputProcessor);
    }

    @Override
    protected void processStageExit(int lifeCycleCode) {
        switch (lifeCycleCode) {
            case ConstructorStage.START_BUTTON_CLICKED_EXIT:
                GameState.activateScreen(GameState.TESTER_SCREEN);
                break;
            case GameStage.UNDO_BUTTON_CLICKED_EXIT:
                GameState.activateScreen(GameState.CONSTRUCTOR_SCREEN);
                break;
        }
    }
}
