package com.refreshgames.model.modeldata;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * helper class to control collisions
 */
public class Collisions {
    private static final short CATEGORY_ROBOT = 1;
    private static final short CATEGORY_BORDER = 2;
    private static final short CATEGORY_APERTURE = 4;

    //collide MASK_... collides with CATEGORY_... || CATEGORY_...
    private static final short MASK_ROBOT = CATEGORY_BORDER;
    private static final short MASK_BORDER = CATEGORY_ROBOT;
    private static final short MASK_APERTURE = CATEGORY_APERTURE;

    public static final Filter ROBOT_FILTER = new Filter();

    static {
        ROBOT_FILTER.categoryBits = CATEGORY_ROBOT;
        ROBOT_FILTER.maskBits = MASK_ROBOT;
    }

    public static final Filter BORDER_FILTER = new Filter();

    static {
        BORDER_FILTER.categoryBits = CATEGORY_BORDER;
        BORDER_FILTER.maskBits = MASK_BORDER;
    }

    public static final Filter APERTURE_FILTER = new Filter();

    static {
        APERTURE_FILTER.categoryBits = CATEGORY_APERTURE;
        APERTURE_FILTER.maskBits = MASK_APERTURE;
    }

    public static boolean isApertureCollision(Contact contact) {
        return (isApertureFixture(contact.getFixtureA()) && isApertureFixture(contact.getFixtureB()));
    }

    public static boolean isApertureFixture(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_APERTURE;
    }
}
