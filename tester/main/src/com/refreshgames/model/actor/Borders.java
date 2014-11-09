package com.refreshgames.model.actor;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.model.modeldata.Collisions;
import com.refreshgames.model.modeldata.Material;
import com.refreshgames.model.modeldata.PartModelData;
import com.refreshgames.model.stage.SavedActorList;
import com.refreshgames.view.mediadata.PolygonMediaData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * borderBounds of gameworld
 */
public class Borders extends BaseActor<PartModelData, PolygonMediaData> implements SavedActorList.SaveAble {
    private static final float AVG_SEGMENT_LENGTH = 2;
    private static final float HEIGHT1 = AVG_SEGMENT_LENGTH / 4;
    private static final float HEIGHT2 = AVG_SEGMENT_LENGTH / 2;

    private List<Type> typeList = new ArrayList<Type>();
    private Vector2 lastPoint;
    private Vector2 firstPoint;
    private Rectangle bounds = new Rectangle();
    private Finish finish;

    public Borders() {
        super();

        addSegment(Type.FLAT);
        addSegment(Type.FLAT);
        addSegment(Type.FLAT);
        addSegment(Type.FLAT);
        addSegment(Type.FLAT);
        addSegment(Type.FLAT);
        addSegment(Type.FLAT);
        addSegment(Type.FLAT);
        addSegment(Type.FLAT);
    }

    public Borders(BaseStage stage, List<Type> typeList) {
        this();

        this.typeList = typeList;
        create(stage, new Vector2(-stage.getWidth() / 2, 0));
    }

    public void addSegment(Type type) {
        typeList.add(type);
    }

    public void create(BaseStage stage, Vector2 firstPoint) {
        List<Vector2> pointList = new ArrayList<Vector2>(
                Arrays.asList(new Vector2[]{new Vector2(firstPoint)}));
        for (Type type : typeList) {
            Vector2 lastPoint = pointList.get(pointList.size() - 1);
            for (Vector2 point : type.structure) {
                pointList.add(point.cpy().add(lastPoint));
            }
        }

        Vector2[] pointsArray = pointList.toArray(new Vector2[pointList.size() - 1]);
        calcBounds(pointList);
        init(stage.getWorld().STATIC_BODY_DEF.createAsBorder(Material.WORLD_BORDER, pointsArray),
                new PolygonMediaData(pointsArray));
        modelData.setCollisionFilter(Collisions.BORDER_FILTER);

        //Finish point
        finish = new Finish(stage, getLastPoint().x - Finish.HALF_WIDTH,
                getLastPoint().y + Finish.HALF_HEIGHT);
    }

    private void calcBounds(List<Vector2> pointList) {
        lastPoint = pointList.get(pointList.size() - 1);
        firstPoint = pointList.get(0);
        bounds.x = pointList.get(0).x;
        bounds.width = lastPoint.x - pointList.get(0).x;
        float minY = 0;
        float maxY = 0;
        for (Vector2 point : pointList) {
            minY = Math.min(minY, point.y);
            maxY = Math.max(maxY, point.y);
        }
        bounds.y = minY;
        bounds.height = maxY - minY;
    }

    public static enum Type {
        FLAT(new Vector2[]{new Vector2(AVG_SEGMENT_LENGTH, 0)}),
        UP(new Vector2[]{new Vector2(AVG_SEGMENT_LENGTH, HEIGHT1)}),
        DOWN(new Vector2[]{new Vector2(AVG_SEGMENT_LENGTH, -HEIGHT1)}),
        PIT(new Vector2[]{new Vector2(AVG_SEGMENT_LENGTH, -HEIGHT2), new Vector2(AVG_SEGMENT_LENGTH, HEIGHT2)}),
        PEAK(new Vector2[]{new Vector2(AVG_SEGMENT_LENGTH, HEIGHT2), new Vector2(AVG_SEGMENT_LENGTH, -HEIGHT2)}),

        VERTICAL_UP(new Vector2[]{new Vector2(0, AVG_SEGMENT_LENGTH)}),
        VERTICAL_DOWN(new Vector2[]{new Vector2(0, -AVG_SEGMENT_LENGTH)}),

        BACK(new Vector2[]{new Vector2(-AVG_SEGMENT_LENGTH, 0)});

        private Type(Vector2[] structure) {
            this.structure = structure;
        }

        private Vector2[] structure;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    public Vector2 getLastPoint() {
        return lastPoint;
    }

    public Vector2 getFirstPoint() {
        return firstPoint;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Finish getFinish() {
        return finish;
    }
}
