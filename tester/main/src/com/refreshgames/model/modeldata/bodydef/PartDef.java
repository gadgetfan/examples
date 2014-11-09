package com.refreshgames.model.modeldata.bodydef;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.refreshgames.model.modeldata.Material;
import com.refreshgames.model.modeldata.PartModelData;

/**
 * definition of part
 */
public class PartDef extends BodyDef {
    //TODO: is not thread safe
    private static final PolygonShape POLYGON_SHAPE = new PolygonShape();
    private static final CircleShape CIRCLE_SHAPE = new CircleShape();
    private static final MaterialFixtureDef FIXTURE_DEF = new MaterialFixtureDef();

    private World world;

    public PartDef(World world, BodyType type) {
        this.world = world;
        this.type = type;
    }

    public PartModelData createAsRectangle(Material material, float x, float y,
                                           float halfWidth, float halfHeight, float angleDegree) {
        position.set(x, y);
        angle = angleDegree * MathUtils.degreesToRadians;

        POLYGON_SHAPE.setAsBox(halfWidth, halfHeight);
        FIXTURE_DEF.shape = POLYGON_SHAPE;
        Body body = world.createBody(this);

        FIXTURE_DEF.setMaterial(material);
        body.createFixture(FIXTURE_DEF);

        return new PartModelData(material, body, halfWidth, halfHeight);
    }

    public PartModelData createAsCircle(Material material, float x, float y, float radius,
                                        float angleDegree) {
        position.set(x, y);
        angle = angleDegree * MathUtils.degreesToRadians;

        CIRCLE_SHAPE.setRadius(radius);
        FIXTURE_DEF.shape = CIRCLE_SHAPE;
        Body body = world.createBody(this);

        FIXTURE_DEF.setMaterial(material);
        body.createFixture(FIXTURE_DEF);

        return new PartModelData(material, body, radius, radius);
    }

    public PartModelData createAsBorder(Material material, Vector2[] vertices) {
        Body body = world.createBody(this);

        ChainShape chainShape = new ChainShape();
        chainShape.createChain(vertices);
        FIXTURE_DEF.shape = chainShape;
        FIXTURE_DEF.setMaterial(material);

        Fixture fixture = body.createFixture(FIXTURE_DEF);

        return new PartModelData(Material.WORLD_BORDER, body, 0, 0);
    }
}
