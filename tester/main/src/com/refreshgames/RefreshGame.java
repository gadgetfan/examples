package com.refreshgames;

/**
 * Main class for game
 */
public class RefreshGame extends BaseGame {
    @Override
    public void create() {
        GameState.setGame(this);
        GameState.activateScreen(GameState.CONSTRUCTOR_SCREEN);
    }
}
