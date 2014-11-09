package com.sprotect.view.activity.photographsign;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.sprotect.R;
import com.sprotect.SProtectApplication;
import com.sprotect.model.camera.CameraManagerTakePicture;
import com.sprotect.view.activity.CameraActivity;
import com.sprotect.view.activity.photographsign.overlayview.CutSignHandler;
import com.sprotect.view.activity.photographsign.overlayview.FindQrFinderHandler;
import com.sprotect.view.activity.photographsign.overlayview.OverlayView;
import com.sprotect.model.camera.CameraManager;
import com.sprotect.model.camera.CameraManagerPreview;

/**
 * takes picture of the sign
 */
public class FindSignActivity extends CameraActivity {
    private CameraManager cameraManager;
    private FindQrFinderHandler findQrFinderHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        removeHeader();
        createView();
    }

    private void createView() {
        setContentView(R.layout.photograph_sign);

        FrameLayout preview = (FrameLayout) findViewById(R.id.preview);

        findQrFinderHandler = new FindQrFinderHandler();
        CutSignHandler cutSignHandler = new CutSignHandler((SProtectApplication) getApplication());

        //cameraManager = new CameraManagerPreview();
        cameraManager = new CameraManagerTakePicture();

        cameraManager.setPreviewCallback(findQrFinderHandler);
        cameraManager.setPictureTakenCallback(cutSignHandler);

        OverlayView overlayView = new OverlayView(this);
        VideoPreview videoPreview = new VideoPreview(this);

        preview.addView(videoPreview);
        preview.addView(overlayView);
    }

    private void removeHeader() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public FindQrFinderHandler getVideoPreviewData() {
        return findQrFinderHandler;
    }

    @Override
    protected void onStart() {
        super.onStart();

        cameraManager.createCamera();

        /*final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (getCameraManager().canTakePicture()) {
                            getCameraManager().takePicture();
                            timer.cancel();
                        }
                    }
                }, 500, 500);*/
    }

    @Override
    protected void onStop() {
        cameraManager.releaseCamera();

        super.onStop();
    }
}
