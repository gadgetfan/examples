package com.refreshgames.model.modeldata.bodydef;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.refreshgames.model.modeldata.Material;

public class MaterialFixtureDef extends FixtureDef {
    public void setMaterial(Material material) {
        density = material.density;
        friction = material.friction;
        restitution = material.restitution;
    }
}
