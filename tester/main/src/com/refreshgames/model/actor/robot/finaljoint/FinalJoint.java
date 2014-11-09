package com.refreshgames.model.actor.robot.finaljoint;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Disposable;
import com.refreshgames.GameState;
import com.refreshgames.model.actor.robot.PreJoint;
import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.model.modeldata.aperturejoint.FinalJointModelData;
import com.refreshgames.model.stage.constructor.ConstructorStage;
import com.refreshgames.model.stage.tester.EnergyConsumptionAction;
import com.refreshgames.model.stage.tester.TesterStage;
import com.refreshgames.view.assets.Assets;
import com.refreshgames.view.mediadata.TextureArrayMediaData;

/**
 * Final joint between parts
 */
public class FinalJoint extends BaseActor<FinalJointModelData, TextureArrayMediaData> implements Disposable {

    private static final float POWER = GameState.STANDARD_POWER;

    private static enum State {NUT, MOTOR_CLOCK, MOTOR_UNCLOCK, AXIS}

    private State state;

    private FinalJointConsumptionAction consumptionAction = new FinalJointConsumptionAction(POWER);

    public FinalJoint(BaseStage stage, PreJoint preJoint) {
        super(new FinalJointModelData(preJoint.getModelData()),
                new TextureArrayMediaData(stage.getAtlas(),
                        "part/nut", "part/motor_clock", "part/motor_unclock", "part/axis"));

        mediaData.setSpriteModel(modelData);

        preJoint.getModelData().getActiveFixture().setUserData(this);
        preJoint.getModelData().getPassiveFixture().setUserData(this);
        setState(State.values()[0]);
    }

    @Override
    protected void createListeners() {
        super.createListeners();

        addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (getStage().getClass().equals(ConstructorStage.class)) {
                    nextState();
                } else if (getStage().getClass().equals(TesterStage.class)) {
                    toggleMotor();
                }

                return false;
            }
        });
    }

    private void nextState() {
        if (state.ordinal() < (State.values().length - 1)) {
            setState(State.values()[state.ordinal() + 1]);
        } else {
            setState(State.values()[0]);
        }
    }

    private void setState(State newState) {
        switch (newState) {
            case NUT:
            case MOTOR_CLOCK:
            case MOTOR_UNCLOCK:
                modelData.enableMotor();
                break;
            case AXIS:
                modelData.disableMotor();
                break;
        }

        mediaData.setIndex(newState.ordinal());

        state = newState;
    }

    private void toggleMotor() {
        switch (state) {
            case MOTOR_CLOCK:
            case MOTOR_UNCLOCK:
                if (modelData.isMotorRunning()) {
                    stopMotor();
                    removeAction(consumptionAction);
                } else {
                    Assets.SPARKLE.playLoud();
                    consumptionAction.init();
                    addAction(consumptionAction);
                }
                break;
        }
    }

    private void startMotor() {
        switch (state) {
            case MOTOR_CLOCK:
                modelData.startMotor(true);
                break;
            case MOTOR_UNCLOCK:
                modelData.startMotor(false);
                break;
        }

        Assets.playMusic(Assets.MOTOR);
    }

    private void stopMotor() {
        if (modelData.isMotorRunning()) {
            //Assets.BRAKE.playStandard();

            modelData.stopMotor();
            Assets.MOTOR.stop();
        }
    }

    private class FinalJointConsumptionAction extends EnergyConsumptionAction {
        private FinalJointConsumptionAction(float power) {
            super(power);
        }

        @Override
        protected void onStartWork() {
            startMotor();
        }

        @Override
        protected void onEnergyIsOver() {
            stopMotor();
        }
    }

    @Override
    public void dispose() {
        stopMotor();
    }
}
