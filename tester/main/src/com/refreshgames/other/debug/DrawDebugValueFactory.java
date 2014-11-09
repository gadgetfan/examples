package com.refreshgames.other.debug;

/**
 * converts DebugValue in DrawDebugValue
 */
public class DrawDebugValueFactory {
    private DebugActor debugActor;

    public DrawDebugValueFactory(DebugActor debugActor) {
        this.debugActor = debugActor;
    }

    public DrawDebugValue produceValue(DebugValue debugValue, float timeLength) {
        return new DrawDebugValue(
                debugActor.getWidth() * (debugValue.getAdoptedValue() + 0.5f),
                debugActor.getHeight() * (timeLength - (long) timeLength),
                debugValue.getColor()
        );
    }
}
