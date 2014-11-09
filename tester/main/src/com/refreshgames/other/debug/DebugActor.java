package com.refreshgames.other.debug;

import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.modeldata.ModelData;
import com.refreshgames.view.mediadata.MediaData;

import java.util.List;

/**
 * Debug actor
 */
public class DebugActor extends BaseActor {
    /**
     * k to transform time in length by axis
     */
    private static float K_TIME = .05f;
    private static float DELAY_TIME = .1f;

    private float timeLength;
    private DrawDebugValueFactory drawDebugValueFactory;
    private List<DrawDebugValue> drawDebugValueList;

    public DebugActor(MediaData mediaData, ModelData modelData) {
        super(modelData, mediaData);
    }

    @Override
    protected void createListeners() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /*private DebugActor() {
        timeLength = 0;
        drawDebugValueFactory = new DrawDebugValueFactory(this);
        drawDebugValueList = new ArrayList<DrawDebugValue>();
    }

    public static DebugActor create(BaseStage stage, float x, float y) {
        DebugActor debugActor = new DebugActor();
        debugActor.setWidth(stage.getHalfWidth());
        debugActor.setHeight(stage.getHeight());

        debugActor.init(stage, x, y);

        return debugActor;
    }

    @Override
    protected void createMediaData(BaseStage stage) {
        mediaData = new DebugMediaData(this);
    }

    @Override
    protected void createBody(BodyFactory bodyFactory) {
        body = bodyFactory.createStaticBody(this);
        body.getFixtureList().get(0).setSensor(true);
    }

    @Override
    protected void createListeners() {
        addListener(new DebugListener() {
            @Override
            protected boolean onDebugValue(DebugValue debugValue) {
                DrawDebugValue drawDebugValue = drawDebugValueFactory.produceValue(debugValue, timeLength);
                if (drawDebugValueList.size() > 0
                        && drawDebugValue.y < drawDebugValueList.get(drawDebugValueList.size() - 1).y) {
                    drawDebugValueList.clear();
                }
                drawDebugValueList.add(drawDebugValue);
                return true;
            }
        });
    }

    @Override
    public void act(float deltaTime) {
        timeLength += K_TIME * deltaTime;

        super.act(deltaTime);
        delay();
    }

    private void delay() {
        try {
            Thread.sleep((long) (DELAY_TIME * 1000));
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public float getTimeLength() {
        return timeLength;
    }

    public List<DrawDebugValue> getDrawDebugValueList() {
        return drawDebugValueList;
    }*/
}
