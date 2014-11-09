package com.refreshgames.model.actor.controls;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.view.assets.Assets;

/**
 * Indicator of energy to use for motors
 */
public class EnergyIndicator extends Image {
    private static final float WIDTH = 0.5f;
    private static final float MAX_ENERGY = 10;

    private float energy;
    private final float maxHeight;

    public EnergyIndicator(BaseStage stage, float startEnergy) {
        super(new TextureRegionDrawable(stage.getAtlas().findRegion("control/indicator")));
        this.energy = Math.min(MAX_ENERGY, startEnergy);
        this.maxHeight = stage.getHeight();

        setWidth(WIDTH);
        calcParams();
    }

    public boolean hasEnergy(float neededEnergy) {
        return (this.energy >= neededEnergy);
    }

    public void addEnergy(float addedEnergy) {
        this.energy += addedEnergy;
        if (this.energy > MAX_ENERGY) {
            this.energy = MAX_ENERGY;
        }

        Assets.JUMP.playStandard();
        calcParams();
    }

    public boolean removeEnergy(float neededEnergy) {
        if (hasEnergy(neededEnergy)) {
            energy -= neededEnergy;
            calcParams();
            return true;
        } else {
            //TODO: blinking of indicator
            return false;
        }
    }

    private void calcParams() {
        setHeight(maxHeight * energy / MAX_ENERGY);
        invalidate();
    }

}
