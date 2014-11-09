package com.sprotect.view.activity.photographsign;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.sprotect.model.android.ExceptionsUtils;
import com.sprotect.model.camera.CameraManager;

import java.io.IOException;

/**
 * preview from camera
 */
public class VideoPreview extends SurfaceView implements SurfaceHolder.Callback {
    VideoPreview(Context context) {
        super(context);

        initPreviewHolder();
    }

    private void initPreviewHolder() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        //getCameraManager().createCamera();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        //getCameraManager().releaseCamera();
    }


    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (getCameraManager().inPreview()) {
            getCameraManager().stopPreview();
        }

        Camera.Size size = getCameraManager().setPreferredSize(w, h);
        //((FrameLayout) getParent()).getLayoutParams().width = size.width; //TODO: need center Overlay View correctly after defining size of Video preview
        //((FrameLayout) getParent()).getLayoutParams().height = size.height;

        try {
            getCameraManager().startPreview(holder);
        } catch (IOException e) {
            ExceptionsUtils.showAndIgnore(e);
        }
    }

    private CameraManager getCameraManager() {
        return ((FindSignActivity)getContext()).getCameraManager();
    }
}
