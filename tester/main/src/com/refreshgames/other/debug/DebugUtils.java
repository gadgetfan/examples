package com.refreshgames.other.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import java.util.List;

/**
 * static class with utils for debugging
 */
public class DebugUtils {
    private static int MAX_DEBUG_MESSAGES_COUNT = 10;
    public static final boolean DEBUG_DETECTOR = false;
    public static final boolean DEBUG_BOX2D_RENDERING = false;
    /**
     * use TextDebugActor
     */
    public static final boolean TEXT_DEBUG = true;

    private static DebugActor debugActor;
    private static List<String> debugMessageList = new LimitedQueue<String>(MAX_DEBUG_MESSAGES_COUNT);

    static {
        if (DebugUtils.DEBUG_DETECTOR) {
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
        }
    }

    public static void addDebugMessage(String debugMessage) {
        debugMessageList.add(debugMessage);
    }

    public static List<String> getDebugMessageList() {
        return debugMessageList;
    }

    public static void init(DebugActor debugActor) {
        DebugUtils.debugActor = debugActor;
    }

    public static void sendDebugValue(DebugValue debugValue) {
        if (debugActor != null) {
            DebugListener.DebugEvent event = new DebugListener.DebugEvent();
            event.setDebugValue(debugValue);
            debugActor.fire(event);
        }
    }
}
