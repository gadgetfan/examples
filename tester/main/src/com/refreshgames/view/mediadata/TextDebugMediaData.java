package com.refreshgames.view.mediadata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.refreshgames.other.debug.DebugUtils;

/**
 * displays debug messages
 */
public class TextDebugMediaData extends EmptyMediaData {
    private SpriteBatch spriteBatch = new SpriteBatch();
    private BitmapFont font = new BitmapFont();
    private float screenHeight;
    private StringBuilder debugMessages = new StringBuilder();

    private long startFpsTime;
    private int fps;

    public TextDebugMediaData(float screenWidth, float screenHeight) {
        this.screenHeight = screenHeight;
        this.spriteBatch.setProjectionMatrix((new Matrix4()).setToOrtho2D(0, 0, screenWidth, screenHeight));
        this.font.setColor(Color.GREEN);
        this.startFpsTime = System.currentTimeMillis();
    }

    public void draw() {
        debugMessages.setLength(0);

        debugMessages.append(calcFps());
        debugMessages.append('\n');

        for (String debugMessage : DebugUtils.getDebugMessageList()) {
            debugMessages.append(debugMessage);
            debugMessages.append('\n');
        }
        spriteBatch.begin();
        font.drawMultiLine(spriteBatch, debugMessages.toString(), 0, screenHeight);
        spriteBatch.end();
    }

    private int calcFps() {
        if (System.currentTimeMillis() - startFpsTime > 1000) {
            fps = Gdx.graphics.getFramesPerSecond();
            startFpsTime = System.currentTimeMillis();
        }

        return fps;
    }
}
