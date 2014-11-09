package com.refreshgames.model.base.world;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.refreshgames.model.actor.Part;
import com.refreshgames.model.modeldata.bodydef.PartDef;

import java.util.Iterator;

/**
 * ancestor of all worlds
 */
public abstract class BaseWorld {
    public final PartDef STATIC_BODY_DEF;
    public final PartDef DYNAMIC_BODY_DEF;

    protected World world;

    private FindTouchedBodyCallback findTouchedBodyCallback = new FindTouchedBodyCallback();
    private Box2DDebugRenderer box2DDebugRenderer;

    public BaseWorld(Vector2 gravity) {
        world = new World(gravity, true);
        STATIC_BODY_DEF = new PartDef(world, BodyDef.BodyType.StaticBody);
        DYNAMIC_BODY_DEF = new PartDef(world, BodyDef.BodyType.DynamicBody);

        addContactListener();
    }

    public void dispose() {
        if (world != null) {
            world.dispose();
            world = null;
        }
    }

    protected abstract void addContactListener();

    public void act(float timeStep) {
        world.step(0.02f/*timeStep*/, 4, 1);
    }

    public void setGravity(Vector2 gravity) {
        world.setGravity(gravity);
        Iterator<Body> bodyIterator = world.getBodies();
        while (bodyIterator.hasNext()) {
            bodyIterator.next().setAwake(true);
        }
    }


    public Part calcOverlapped(float x, float y) {
        findTouchedBodyCallback.init(x, y);
        world.QueryAABB(findTouchedBodyCallback,
                x - FindTouchedBodyCallback.TOUCH_FIND_SIZE / 2, y - FindTouchedBodyCallback.TOUCH_FIND_SIZE / 2,
                x + FindTouchedBodyCallback.TOUCH_FIND_SIZE / 2, y + FindTouchedBodyCallback.TOUCH_FIND_SIZE / 2);

        return findTouchedBodyCallback.getNearestPart();
    }

    private Box2DDebugRenderer getBox2DDebugRenderer() {
        if (box2DDebugRenderer == null) {
            box2DDebugRenderer = new Box2DDebugRenderer();
        }

        return box2DDebugRenderer;
    }

    public void render(Matrix4 matrix) {
        getBox2DDebugRenderer().render(world, matrix);
    }

}
