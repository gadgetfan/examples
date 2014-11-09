package com.refreshgames.other.screens;

import com.refreshgames.GameState;
import com.refreshgames.model.base.BaseScreen;
import com.refreshgames.model.stage.GameStage;
import com.refreshgames.model.stage.tester.TesterStage;
import com.refreshgames.view.assets.Assets;

/**
 * Screen to test robot
 */
public class TesterScreen extends BaseScreen<TesterStage> {
    @Override
    protected TesterStage createStage(int width, int height) {
        return new TesterStage(width, height, Assets.textureAtlas, inputProcessor);
    }

    @Override
    protected void processStageExit(int lifeCycleCode) {
        switch (lifeCycleCode) {
            case TesterStage.LEVEL_FINISHED_EXIT:
                //should be before new world creation in GameState.activateScreen(GameState.CONSTRUCTOR_SCREEN);
                disposeStage();
                if (GameState.nextLevel()) {
                    GameState.activateScreen(GameState.CONSTRUCTOR_SCREEN);
                } else {
                    GameState.activateScreen(GameState.GAME_OVER_SCREEN);
                }

                break;

            case GameStage.UNDO_BUTTON_CLICKED_EXIT:
                disposeStage();
                GameState.activateScreen(GameState.CONSTRUCTOR_SCREEN);

                break;
        }
    }
}
