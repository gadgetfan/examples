package com.refreshgames.model.stage.constructor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.stage.GameStageInputListener;

/**
 * implementation of InputListener for ConstructorStage
 */
class ConstructorStageInputListener extends GameStageInputListener {
    private static final float POINT_SPEED = 30f;

    private static enum DragKind {DRAG_NONE, DRAG_ACTOR, DRAG_CAMERA}

    private DragKind dragKind;
    private BaseActor hitActor;
    private final ScreenPosition oldPointerScreenPosition;
    private final ScreenPosition pointerScreenPosition;
    private final Vector2 cameraPosition = new Vector2();
    private final Rectangle cameraBounds;
    private final Camera camera;

    public ConstructorStageInputListener(ConstructorStage stage, Rectangle cameraBounds) {
        super(stage);

        this.camera = stage.getCamera();
        this.cameraBounds = cameraBounds;
        this.oldPointerScreenPosition = new ScreenPosition(camera);
        this.pointerScreenPosition = new ScreenPosition(camera);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        dragKind = DragKind.DRAG_NONE;
        boolean result = false;

        hitActor = calcHitFinalJoint(x, y);
        if (hitActor == null) {
            hitActor = stage.calcHitPart(x, y);
        }
        if (hitActor != null) {
            event.setBubbles(false);
            hitActor.fire(event);
            result = event.isHandled();
            if (result) {
                dragKind = DragKind.DRAG_ACTOR;
            }
        }

        if (!result) {
            dragKind = DragKind.DRAG_CAMERA;
            oldPointerScreenPosition.setUnproject(x, y);
            result = true;
        }

        //DebugUtils.addDebugMessage("touchDown " + dragKind);
        return result;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        switch (dragKind) {
            case DRAG_ACTOR:
                event.setBubbles(false);
                hitActor.fire(event);

                break;
            case DRAG_CAMERA:
                pointerScreenPosition.setUnproject(x, y);
                final float deltaX = oldPointerScreenPosition.x - pointerScreenPosition.x;
                final float deltaY = oldPointerScreenPosition.y - pointerScreenPosition.y;
                cameraPosition.set(camera.position.x, camera.position.y);
                cameraPosition.add(deltaX * POINT_SPEED, -deltaY * POINT_SPEED);

                //DebugUtils.addDebugMessage("Camera translate " + deltaX);
                if (cameraPosition.x < cameraBounds.x) {
                    cameraPosition.x = cameraBounds.x;
                } else if (cameraPosition.x > (cameraBounds.x + cameraBounds.width)) {
                    cameraPosition.x = cameraBounds.x + cameraBounds.width;
                }
                if (cameraPosition.y < cameraBounds.y) {
                    cameraPosition.y = cameraBounds.y;
                } else if (cameraPosition.y > (cameraBounds.y + cameraBounds.height)) {
                    cameraPosition.y = cameraBounds.y + cameraBounds.height;
                }

                camera.position.x = cameraPosition.x;
                camera.position.y = cameraPosition.y;
                camera.update();
                oldPointerScreenPosition.setUnproject(x, y);

                break;
        }

    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        switch (dragKind) {
            case DRAG_ACTOR:
                event.setBubbles(false);
                hitActor.fire(event);
                hitActor = null;
                break;
            case DRAG_CAMERA:
                break;
        }

        //DebugUtils.addDebugMessage("touchUp " + dragKind);
    }
}