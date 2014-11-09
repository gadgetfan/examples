package com.sprotect.model.camera;// MaiseyenkaDP gdfan 22.09.12 11:53

import android.graphics.ImageFormat;
import android.hardware.Camera;

/**
 * Manager to work with camera. Uses preview to get sign
 */
public class CameraManagerPreview extends CameraManager {
    private byte[] videoPreviewBuffer;
    private PictureTakenCallback pictureTakenCallback;

    public CameraManagerPreview() {
        super();

        this.videoPreviewBuffer = new byte[0];
    }

    public void setPictureTakenCallback(final PictureTakenCallback pictureTakenCallback) {
        this.pictureTakenCallback = pictureTakenCallback;
    }

    public void setPreviewCallback(final CameraPreviewCallback cameraPreviewCallback) {
        setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                if (getState() == CameraState.IN_FOCUS
                        && cameraPreviewCallback.isSignOnPreview(data, CameraManagerPreview.this)) {
                    photoIsMade();
                    takePictureFromData(data);
                } else {
                    camera.addCallbackBuffer(data);
                }
            }
        });
    }

    @Override
    protected void setPreviewParams() {
        setFlash(Camera.Parameters.FLASH_MODE_TORCH);
    }

    public void takePicture() {
        assert false;
        //TODO: remove this method from CameraManager
    }

    private void takePictureFromData(byte[] data) {
        pictureTakenCallback.onPictureTaken(data, getPreviewRoiRect(), this, true);
    }


    protected void startPreviewCallback() {
        if (getPreviewCallback() != null) {
            getCamera().setPreviewCallbackWithBuffer(getPreviewCallback());

            Camera.Parameters parameters = getCamera().getParameters();
            Camera.Size size = parameters.getPreviewSize();
            int bufferLength = size.width * size.height * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
            if (bufferLength != videoPreviewBuffer.length) {
                videoPreviewBuffer = new byte[bufferLength];
            }
            getCamera().addCallbackBuffer(videoPreviewBuffer);
        }
    }

    @Override
    protected int getInFocusDelay() {
        return 100;
    }
}