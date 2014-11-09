package com.refreshgames.model.base.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.refreshgames.model.actor.Part;

/**
 * finds nearest touched body
 */
public class FindTouchedBodyCallback implements QueryCallback {
    /**
     * sizes of fixture to calc overlap with other fixtures to find touched object
     */
    static final float TOUCH_FIND_SIZE = 0.2f;

    private Vector2 centerPosition = new Vector2();
    private Part nearestPart;
    private float minLength2;

    @Override
    public boolean reportFixture(Fixture fixture) {
        Object object = fixture.getBody().getUserData();
        if (object instanceof Part) {
            Part part = (Part) object;
            if (nearestPart == null) {
                nearestPart = part;
            } else {
                float length2 = part.getModelData().getCenter().cpy().sub(centerPosition).len2(); //TODO: optimization
                if (length2 < minLength2) {
                    minLength2 = length2;
                    nearestPart = part;
                }
            }
        }

        return true;
    }

    public void init(float x, float y) {
        centerPosition.set(x, y);
        nearestPart = null;
    }

    public Part getNearestPart() {
        return nearestPart;
    }
}
