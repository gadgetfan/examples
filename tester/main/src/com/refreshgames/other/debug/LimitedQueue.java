package com.refreshgames.other.debug;

import java.util.LinkedList;

/**
 * Limits elements count. Is NOT thread-safe
 *
 * @param <E>
 */
public class LimitedQueue<E> extends LinkedList<E> {

    private int limit;

    public LimitedQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(E o) {
        super.add(o);
        while (size() > limit) {
            super.remove();
        }
        return true;
    }
}