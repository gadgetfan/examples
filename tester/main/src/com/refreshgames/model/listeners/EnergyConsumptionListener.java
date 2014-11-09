package com.refreshgames.model.listeners;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/**
 * handles consumption of energy to work robot parts
 */
public abstract class EnergyConsumptionListener implements EventListener {
    @Override
    public boolean handle(Event event) {
        if (!(event instanceof EnergyConsumptionEvent)) return false;

        EnergyConsumptionEvent energyConsumptionEvent = (EnergyConsumptionEvent) event;
        if (!onEnergyConsumption(energyConsumptionEvent.energy)) {
            energyConsumptionEvent.cancel();
        }

        return true;
    }

    /**
     * returns false, if is not enough energy
     * @param energy
     * @return
     */
    protected abstract boolean onEnergyConsumption(float energy);

    //collision event
    static public class EnergyConsumptionEvent extends NotBubblesEvent {
        private float energy;

        public void setEnergy(float energy) {
            this.energy = energy;
        }
    }
}
