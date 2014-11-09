package com.sprotect.model.camera;// MaiseyenkaDP gdfan 22.09.12 11:53

import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import com.sprotect.SharedConstants;
import com.sprotect.model.android.ExceptionsUtils;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Manager to work with camera
 */
public abstract class CameraManager {
    /**
     * size of red rectangle on the preview screen
     */
    public static final int PREVIEW_ROI_REC_SIZE = 200;

    /**
     * tag for logging
     */
    public static final String TAG = "CameraManager";

    CameraState state;

    private Camera camera;
    private Camera.PreviewCallback previewCallback;

    private Rect previewRoiRect;
    private Rect previewRect;
    //private byte[] takePictureBuffer;

    public CameraManager() {
        this.setState(CameraState.NO_CAMERA);

        this.previewRoiRect = new Rect();
        this.previewRect = new Rect();

        //this.takePictureBuffer = new byte[0];
    }

    public abstract void setPictureTakenCallback(final PictureTakenCallback pictureTakenCallback);

    public abstract void setPreviewCallback(final CameraPreviewCallback cameraPreviewCallback);

    public void createCamera() {
        Log.e(TAG, "createCamera");
        ExceptionsUtils.assertStateIsEqual(state, CameraState.NO_CAMERA, "createCamera");
        camera = Camera.open();
        clearPreviewCallback();
        setState(CameraState.CREATED);
    }

    public void startPreview(SurfaceHolder holder) throws IOException {
        Log.e(TAG, "startPreview");
        ExceptionsUtils.assertStateIsEqual(state, CameraState.CREATED, "startPreview");

        setPreviewParams();
        startPreviewCallback();
        camera.setPreviewDisplay(holder);

        camera.startPreview();
        setState(CameraState.PREVIEW);

        startAutoFocus();
    }

    protected abstract void setPreviewParams();

    public void stopPreview() {
        Log.e(TAG, "stopPreview");
        ExceptionsUtils.assertStateIsAfterOrEqual(state, CameraState.PREVIEW, "stopPreview");
        try {
            //flashOff();
            camera.cancelAutoFocus();
            camera.stopPreview();
            setState(CameraState.CREATED);
            clearPreviewCallback();
        } catch (Exception e) {
            ExceptionsUtils.showAndIgnore(e);
        }
    }

    public void releaseCamera() {
        Log.e(TAG, "releaseCamera");
        ExceptionsUtils.assertStateIsAfterOrEqual(state, CameraState.CREATED, "releaseCamera");
        if (camera != null) {
            stopPreview();
            setFlash(Camera.Parameters.FLASH_MODE_OFF);

            camera.release();
            setState(CameraState.NO_CAMERA);
            camera = null;
        }
    }

    protected abstract void takePicture();

    protected void setFlash(String flashMode) {
        Log.e(TAG, "setFlash");
        ExceptionsUtils.assertStateIsAfterOrEqual(state, CameraState.CREATED, "setFlash");

        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(flashMode);

        camera.setParameters(parameters);
    }

    /**
     * try to set preferredSize and returns most similar size
     */
    public Camera.Size setPreferredSize(int width, int height) {
        Log.e(TAG, "setPreferredSize");
        ExceptionsUtils.assertStateIsEqual(state, CameraState.CREATED, "setPreferredSize");

        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        Camera.Size optimalPreviewSize = getOptimalPreviewSize(previewSizes, width, height);
        parameters.setPreviewSize(optimalPreviewSize.width, optimalPreviewSize.height);
        calcPreviewRoiRect(optimalPreviewSize.width, optimalPreviewSize.height);
        calcPreviewRect(optimalPreviewSize.width, optimalPreviewSize.height);

        camera.setParameters(parameters);

        return camera.getParameters().getPreviewSize();
    }

    private void calcPreviewRect(int width, int height) {
        previewRect.set(0, 0, width, height);
    }

    private void calcPreviewRoiRect(int w, int h) {
        int xCenter = w / 2;
        int yCenter = h / 2;
        previewRoiRect.set(xCenter - PREVIEW_ROI_REC_SIZE / 2, yCenter - PREVIEW_ROI_REC_SIZE / 2,
                xCenter + PREVIEW_ROI_REC_SIZE / 2, yCenter + PREVIEW_ROI_REC_SIZE / 2);
    }

    public Rect getPreviewRoiRect() {
        return previewRoiRect;
    }

    protected abstract void startPreviewCallback();

    private void clearPreviewCallback() {
        camera.setPreviewCallback(null);
    }

    /*private void setTakePictureBuffer() {
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size size = parameters.getPictureSize();
        int bufferLength = size.width * size.height * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
        if (bufferLength != takePictureBuffer.length) {
            takePictureBuffer = new byte[bufferLength];
        }

        camera.addCallbackBuffer(null);
        camera.addCallbackBuffer(takePictureBuffer);
    }*/

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        Camera.Size bestSize = null;
        boolean isFoundProportional = false;
        for (Camera.Size size : sizes) {
            boolean isProportionalSizes = (Math.abs(((float) size.width / size.height
                    - (float) w / h)) < 0.1);
            if ((bestSize == null)
                    || (isFoundProportional && isProportionalSizes
                        && (Math.abs((size.width - w) * (size.height - h)) < Math.abs((bestSize.width - w) * (bestSize.height - h))))
                    || (!isFoundProportional && isProportionalSizes)) {
                bestSize = size;
                isFoundProportional = (isFoundProportional ? true : isProportionalSizes);
            }
        }

        return bestSize;
    }

    private void startAutoFocus() {
        Log.e(TAG, "startAutoFocus");
        if (state.isAfterOrEqual(CameraState.PREVIEW)) {
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    Log.e(TAG, "onAutoFocus start");
                    if (state.isAfterOrEqual(CameraState.PREVIEW)) {
                        if (!state.isAfterOrEqual(CameraState.NEED_TAKE_PHOTO)) {
                            setState(CameraState.IN_FOCUS);
                            final Timer findSignTimer = new Timer();
                            findSignTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Log.e(TAG, "findSignTimer start");
                                    findSignTimer.cancel();
                                    if (state.equals(CameraState.IN_FOCUS)) {
                                        setState(CameraState.PREVIEW);
                                    }
                                    startAutoFocus();
                                    Log.e(TAG, "findSignTimer finish");
                                }
                            }, getInFocusDelay());
                        } else {
                            if (state == CameraState.NEED_TAKE_PHOTO) {
                                Log.e(TAG, "try take picture");
                                takePicture();
                            }
                        }
                    }
                    Log.e(TAG, "onAutoFocus finish");
                }
            });
        }
    }

    protected abstract int getInFocusDelay();

    public Rect getPreviewRect() {
        return previewRect;
    }

    protected void setPreviewCallback(Camera.PreviewCallback previewCallback) {
        this.previewCallback = previewCallback;
    }

    protected Camera getCamera() {
        return camera;
    }

    protected Camera.PreviewCallback getPreviewCallback() {
        return previewCallback;
    }

    protected CameraState getState() {
        return state;
    }

    protected void photoIsMade() {
        Log.e(TAG, "photoIsMade");
        ExceptionsUtils.assertStateIsAfterOrEqual(state, CameraState.PREVIEW, "photoIsMade"); //TODO: try CameraState.IN_FOCUS
        setState(CameraState.PHOTO_MADE);
    }

    protected void needTakePhoto() {
        Log.e(TAG, "needTakePhoto");
        ExceptionsUtils.assertStateIsAfterOrEqual(state, CameraState.PREVIEW, "needTakePhoto"); //TODO: try CameraState.IN_FOCUS
        setState(CameraState.NEED_TAKE_PHOTO);
    }

    public boolean inPreview() {
        return state.isAfterOrEqual(CameraState.PREVIEW);
    }

    private void setState(CameraState state) {
        Log.e(TAG, "SetState to " + state.toString());
        this.state = state;
    }
}
