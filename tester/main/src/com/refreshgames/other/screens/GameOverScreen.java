package com.refreshgames.other.screens;

import com.refreshgames.GameState;
import com.refreshgames.model.base.BaseScreen;
import com.refreshgames.model.stage.GameOverStage;
import com.refreshgames.model.stage.GameStage;
import com.refreshgames.model.stage.tester.TesterStage;
import com.refreshgames.view.assets.Assets;

/**
 * Game over screen
 */
public class GameOverScreen extends BaseScreen<GameOverStage> {
    @Override
    protected GameOverStage createStage(int width, int height) {
        return new GameOverStage(width, height, Assets.textureAtlas, inputProcessor);
    }

    @Override
    protected void processStageExit(int lifeCycleCode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
