package com.sprotect.model.camera;// MaiseyenkaDP gdfan 22.09.12 11:53

import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;

import java.util.List;

/**
 * Manager to work with camera. Uses takePicture to get sign
 */
public class CameraManagerTakePicture extends CameraManager {
    private Camera.PictureCallback pictureCallback;
    private Camera.PreviewCallback pictureTakenPreviewCallback;
    private Rect pictureRoiRect;


    public CameraManagerTakePicture() {
        super();

        this.pictureRoiRect = new Rect();
    }


    public void setPictureTakenCallback(final PictureTakenCallback pictureTakenCallback) {
        Log.e(TAG, "setPictureTakenCallback");
        this.pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.e(TAG, "picture callback");
                pictureTakenCallback.onPictureTaken(data, pictureRoiRect, CameraManagerTakePicture.this, false);
            }
        };

        this.pictureTakenPreviewCallback = new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                //do nothing
                //TODO: here can I get final picture ?
            }
        };
    }

    public void setPreviewCallback(final CameraPreviewCallback cameraPreviewCallback) {
        Log.e(TAG, "setPreviewCallback");
        setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Log.e(TAG, "onPreviewFrame start");
                if (getState() == CameraState.IN_FOCUS) {
                    Log.e(TAG, "onPreviewFrame IN_FOCUS");
                    if (cameraPreviewCallback.isSignOnPreview(data, CameraManagerTakePicture.this)) {
                        needTakePhoto();
                    }
                }
                Log.e(TAG, "onPreviewFrame finish");
            }
        });
    }

    @Override
    protected void setPreviewParams() {
        setFlash(Camera.Parameters.FLASH_MODE_ON);
    }

    public void takePicture() {
        Log.e(TAG, "takePicture");
        getCamera().setPreviewCallback(pictureTakenPreviewCallback);
        getCamera().takePicture(null, null, pictureCallback);
    }

    protected void startPreviewCallback() {
        Log.e(TAG, "startPreviewCallback");
        getCamera().setPreviewCallback(getPreviewCallback());  //TODO: optimization: use setPreviewCallbackWithBuffer ?
    }

    private void calcPictureRoiRect(Camera.Size pictureSize, Camera.Size previewSize) {
        float kx = (float) pictureSize.width / previewSize.width;
        float ky = (float) pictureSize.height / previewSize.height;

        pictureRoiRect.set(Math.round(getPreviewRoiRect().left * kx), Math.round(getPreviewRoiRect().top * ky),
                Math.round(getPreviewRoiRect().right * kx), Math.round(getPreviewRoiRect().bottom * ky));
    }

    @Override
    public Camera.Size setPreferredSize(int width, int height) {
        Camera.Size result = super.setPreferredSize(width, height);

        //add picture size parameter
        Camera.Parameters parameters = getCamera().getParameters();
        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
        Camera.Size optimalPictureSize = getOptimalPictureSize(pictureSizes, parameters.getPreviewSize());
        parameters.setPictureSize(optimalPictureSize.width, optimalPictureSize.height);
        calcPictureRoiRect(optimalPictureSize, parameters.getPreviewSize());

        getCamera().setParameters(parameters);

        return result;
    }

    /**
     * finds picture size, that has proportions like optimalPreviewSize or the biggest
     *
     * @param pictureSizes
     * @param optimalPreviewSize
     * @return
     */
    private Camera.Size getOptimalPictureSize(List<Camera.Size> pictureSizes, Camera.Size optimalPreviewSize) {
        Camera.Size bestSize = null;
        boolean isFoundProportional = false;
        for (Camera.Size size : pictureSizes) {
            boolean isProportionalSizes = (Math.abs(((float) size.width / size.height
                    - (float) optimalPreviewSize.width / optimalPreviewSize.height)) < 0.1);
            if ((bestSize == null)
                    || (isFoundProportional && isProportionalSizes && (size.width < bestSize.width)) //TODO: now low quality is chosen, change if is needed on (size.width > bestSize.width)
                    || (!isFoundProportional && isProportionalSizes)) {
                bestSize = size;
                isFoundProportional = (isFoundProportional ? true : isProportionalSizes);
            }
        }

        return bestSize;
    }

    @Override
    protected int getInFocusDelay() {
        return 100;
    }

}
