package com.refreshgames.model.stage.tester;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.stage.GameStageInputListener;

/**
 * implementation of InputListener for ConstructorStage
 */
class TesterStageInputListener extends GameStageInputListener {
    private BaseActor hitActor;

    TesterStageInputListener(TesterStage stage) {
        super(stage);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        boolean result = false;

        hitActor = calcHitFinalJoint(x, y);
        if (hitActor != null) {
            event.setBubbles(false);
            hitActor.fire(event);
            result = event.isHandled();
        }

        return result;
    }
}