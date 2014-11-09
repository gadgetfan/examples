package com.refreshgames.other.debug;

/**
 * screen with charts from detectors and others
 */
public class DebugScreen {
}/*extends ConstructorScreen {
    private BaseInputProcessor inputProcessor;
    private DebugStage stage;

    public DebugScreen() {
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
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        if (stage == null) {
            stage = new DebugStage(width, height, inputProcessor);
        }
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
        if (stage != null)
            stage.dispose();
        Gdx.input.setInputProcessor(null);
    }


    @Override
    public void render(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

}
*/