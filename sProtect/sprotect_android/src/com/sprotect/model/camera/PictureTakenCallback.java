package com.sprotect.model.camera;// MaiseyenkaDP gdfan 22.09.12 18:52

import android.graphics.Rect;

/**
 * fires after picture taking
 */
public interface PictureTakenCallback {
    void onPictureTaken(byte[] data, Rect roi, CameraManager cameraManager, boolean rowData);
}
