package com.refreshgames;

import com.refreshgames.model.base.BaseScreen;
import com.refreshgames.other.screens.ConstructorScreen;
import com.refreshgames.other.screens.GameOverScreen;
import com.refreshgames.other.screens.TesterScreen;

/**
 * static class with current game state
 */
public class GameState {
    //const
    public static final float ENERGY_CONSUMPTION_CHECK_INTERVAL = 0.5f;
    public static final float STANDARD_POWER = ENERGY_CONSUMPTION_CHECK_INTERVAL; //standard is 1 energy in 1 second
    private static final int MAX_LEVEL = 4;

    //state
    private static RefreshGame game;

    private static int currentLevel = 1;

    public static final ConstructorScreen CONSTRUCTOR_SCREEN = new ConstructorScreen();
    public static final TesterScreen TESTER_SCREEN = new TesterScreen();
    public static final GameOverScreen GAME_OVER_SCREEN = new GameOverScreen();

    public static void setGame(RefreshGame game) {
        GameState.game = game;
    }

    public static void activateScreen(BaseScreen screen) {
        game.setScreen(screen);
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static boolean nextLevel() {
        currentLevel++;
        return (currentLevel <= MAX_LEVEL);
    }
}
