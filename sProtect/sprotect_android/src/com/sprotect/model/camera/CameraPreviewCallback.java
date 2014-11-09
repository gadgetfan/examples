package com.sprotect.model.camera;// MaiseyenkaDP gdfan 22.09.12 13:38

/**
 * preview of video frame
 */
public interface CameraPreviewCallback {
    boolean isSignOnPreview(final byte[] data, CameraManager cameraManager);
}
