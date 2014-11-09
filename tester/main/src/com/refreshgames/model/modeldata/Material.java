package com.refreshgames.model.modeldata;

/**
 * material for parts
 */
public class Material {
    public static Material WORLD_BORDER = new Material(0, 1f, 0f, 0);
    public static Material STEEL = new Material(7700, 0.5f, 0.1f, 500);
    public static Material PLASTIC = new Material(900, 0.3f, 0.2f, 5000);
    public static Material RUBBER_WITH_AIR = new Material(500, 0.9f, 0.75f, 30000);
    public static Material ETHER = new Material(0, 0f, 0f, 0);

    public float density;
    public float friction;
    public float restitution;
    public float maxSoundImpulse;

    public Material(float density, float friction, float restitution, float maxSoundImpulse) {
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.maxSoundImpulse = maxSoundImpulse;
    }
}
