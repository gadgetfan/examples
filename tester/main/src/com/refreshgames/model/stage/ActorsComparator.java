package com.refreshgames.model.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.refreshgames.model.actor.*;
import com.refreshgames.model.actor.robot.*;
import com.refreshgames.model.actor.robot.finaljoint.FinalJoint;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Defines actors draw order
 * @param <T>
 */
public class ActorsComparator<T> implements Comparator<T> {
    public static final ActorsComparator<SavedActorList.SaveAble> Z_INDEX_SAVEABLE_COMPARATOR =
            new ActorsComparator<SavedActorList.SaveAble>();

    public static final ActorsComparator<Actor> Z_INDEX_ACTOR_COMPARATOR =
            new ActorsComparator<Actor>();

    /**
     * classes higher are classes with higher z-index
     */
    private static final List<? extends Class> zIndexList = Arrays.asList(
            Finish.class,
            PreJoint.class,
            FinalJoint.class,
            Wheel.class,
            Beam3.class,
            Cabin.class,
            Beam5.class,
            Borders.class,
            Background.class);

    @Override
    public int compare(T o1, T o2) {
        return compareIntReverse(zIndexList.indexOf(o1.getClass()), zIndexList.indexOf(o2.getClass()));
    }

    private int compareIntReverse(int a, int b) {
        return a < b ? +1 : a > b ? -1 : 0;
    }
}
