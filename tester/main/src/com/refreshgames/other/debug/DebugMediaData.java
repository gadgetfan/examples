package com.refreshgames.other.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.refreshgames.view.mediadata.EmptyMediaData;

import java.util.List;

/**
 * shows debug data as charts
 */
public class DebugMediaData extends EmptyMediaData {
    private static final Color COLOR_AXIS = new Color(0.2f, 0.2f, 0.2f, 1);
    private List<DrawDebugValue> drawDebugValueList;
    private ShapeRenderer shapeRenderer;


    /*public DebugMediaData(DebugActor debugActor) {
        this.drawDebugValueList = debugActor.getDrawDebugValueList();
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha, Actor drawData) {
        super.draw(batch, parentAlpha, drawData);    //TODO: optimization remove
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        //batch.end();

        DebugActor debugActor = (DebugActor) drawData;
        if (drawDebugValueList.size() > 0) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(COLOR_AXIS);
            shapeRenderer.line(drawData.getHalfWidth() / 2, 0, drawData.getHalfWidth() / 2, drawData.getHalfHeight());
            for (int i = 1; i < drawDebugValueList.size(); ++i) {
                shapeRenderer.setColor(drawDebugValueList.get(i).getColor());
                shapeRenderer.line(drawDebugValueList.get(i - 1).x, drawDebugValueList.get(i - 1).y,
                        drawDebugValueList.get(i).x, drawDebugValueList.get(i).y);
            }
            shapeRenderer.end();
        }

        //batch.begin();
    }*/
}
