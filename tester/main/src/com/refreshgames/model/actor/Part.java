package com.refreshgames.model.actor;

import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.listeners.CollisionListener;
import com.refreshgames.model.modeldata.PartModelData;
import com.refreshgames.other.debug.DebugUtils;
import com.refreshgames.view.assets.Assets;
import com.refreshgames.view.mediadata.SpriteMediaData;

/**
 * Beam to construct all
 */
public abstract class Part extends BaseActor<PartModelData, SpriteMediaData> {

    protected Part(SpriteMediaData mediaData, PartModelData bodyData) {
        super(bodyData, mediaData);

        mediaData.setSpriteModel(bodyData);
    }

    @Override
    protected void createListeners() {
        addListener(new CollisionListener() {
            @Override
            public boolean onCollision(BaseActor collidedActor, float impulse) {
                //play sound only for one contact
                if (Part.this.hashCode() > collidedActor.hashCode() || !(collidedActor instanceof Part)) {
                    float volume = Math.abs(impulse / modelData.getMaterial().maxSoundImpulse);

                    //DebugUtils.addDebugMessage(String.valueOf(impulse));
                    if (volume > 0.2) {
                        Assets.COLLISION_SHORT.addVolume(volume);
                    }
                }

                return true;
            }
        });
    }
}
