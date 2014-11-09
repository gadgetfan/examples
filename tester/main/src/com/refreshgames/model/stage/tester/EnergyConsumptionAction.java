package com.refreshgames.model.stage.tester;

import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.utils.Pools;
import com.refreshgames.GameState;
import com.refreshgames.model.listeners.EnergyConsumptionListener;

/**
 * decrease energy with given power
 */
public abstract class EnergyConsumptionAction extends DelayAction {
    private float power;
    private boolean firstLap;

    public EnergyConsumptionAction(float power) {
        super();
        this.power = power;
        setDuration(GameState.ENERGY_CONSUMPTION_CHECK_INTERVAL);
        init();
    }

    public void init() {
        firstLap = true;
        restart();
    }

    /**
     * is run, when energy is over
     */
    protected abstract void onEnergyIsOver();

    protected abstract void onStartWork();

    @Override
    public boolean act(float delta) {
        if (getTime() == 0) {
            EnergyConsumptionListener.EnergyConsumptionEvent energyConsumptionEvent =
                    Pools.obtain(EnergyConsumptionListener.EnergyConsumptionEvent.class);
            energyConsumptionEvent.setEnergy(power);
            if (getActor().getStage().getRoot().fire(energyConsumptionEvent)) {
                onEnergyIsOver();
                return true;
            } else if (firstLap) {
                firstLap = false;
                onStartWork();
            }
        }

        if (super.act(delta)) {
            restart();
        }

        return false;
    }
}
