package com.refreshgames.view.mediadata;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Renders polygon
 */
public class PolygonMediaData extends EmptyMediaData {
    private ShapeRenderer shapeRenderer;
    private Vector2[] points;

    public PolygonMediaData(Vector2[] points) {
        shapeRenderer = new ShapeRenderer();
        this.points = points;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);    //TODO: optimization remove


        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for (int i = 0; i < points.length - 1; ++i) {
            shapeRenderer.line(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
        }
        shapeRenderer.end();

        batch.begin();
    }
}
