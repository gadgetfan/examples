package com.refreshgames.model.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.refreshgames.model.actor.robot.finaljoint.FinalJoint;
import com.refreshgames.model.base.scene.BaseActor;

/**
 * implementation of InputListener for ConstructorStage
 */
public abstract class GameStageInputListener extends InputListener {
    protected final GameStage stage;

    public GameStageInputListener(GameStage stage) {
        this.stage = stage;
    }

    protected BaseActor calcHitFinalJoint(float x, float y) {
        for (Actor actor : stage.getActors()) {
            if (actor.getClass().equals(FinalJoint.class)) {
                FinalJoint finalJoint = (FinalJoint) actor;
                if (finalJoint.getModelData().hit(x, y)) {
                    return finalJoint;
                }
            }
        }

        return null;
    }

}