package com.sprotect.model.android;// MaiseyenkaDP gdfan 22.09.12 9:53

import android.util.Log;
import com.sprotect.model.camera.CameraManager;
import com.sprotect.model.camera.CameraState;

/**
 * Utils to handle with exceptions
 */
public class ExceptionsUtils {
    public static void showAndIgnore(Exception e) {
        Log.e("-------", e.getMessage(), e);
        /*if (FindSignActivity.getContext() != null) {
            new AlertDialog.Builder(FindSignActivity.getContext()).setMessage(e.getMessage()).create().showAndIgnore();
        }*/
    }

    public static void assertStateIsAfterOrEqual(CameraState currentState, CameraState requiredState, String location) {
        if (!(currentState.isAfterOrEqual(requiredState))) {
            Log.e(CameraManager.TAG, String.format("Incorrect state in %s . Current state %s, required after or equal %s",
                    location, currentState.toString(), requiredState.toString()));
        }
    }

    public static void assertStateIsEqual(CameraState currentState, CameraState requiredState, String location) {
        if (!(currentState.equals(requiredState))) {
            Log.e(CameraManager.TAG, String.format("Incorrect state in %s . Current state %s, required state %s",
                    location, currentState.toString(), requiredState.toString()));
        }
    }
}
