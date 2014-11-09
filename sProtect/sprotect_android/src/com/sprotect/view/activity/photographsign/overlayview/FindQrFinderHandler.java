package com.sprotect.view.activity.photographsign.overlayview;// MaiseyenkaDP gdfan 22.09.12 13:47

import com.sprotect.model.camera.CameraManager;
import com.sprotect.model.camera.CameraPreviewCallback;
import com.sprotect.external.zxing.SignDetectorResult;
import com.sprotect.external.android.zxing.ZxingImageUtils;

/**
 * searches for finders
 */
public class FindQrFinderHandler implements CameraPreviewCallback {
    /*private View overlayView;

    private DetectorResult qrFinder;
    private Bitmap roiBitmap;

    public FindQrFinderHandler(View overlayView) {
        this.overlayView = overlayView;
    }*/

    @Override
    public boolean isSignOnPreview(byte[] data, CameraManager cameraManager) {
        SignDetectorResult currQrFinder = ZxingImageUtils.detectQrFinders(data, cameraManager.getPreviewRect(),
                cameraManager.getPreviewRoiRect());
        if (currQrFinder != null) {
            /*roiBitmap = ZxingImageUtils.getCroppedBitmap(data, cameraManager.getPreviewRect(), cameraManager.getPreviewRoiRect());
            qrFinder = currQrFinder;
            overlayView.invalidate();*/
        }

        return (currQrFinder != null && currQrFinder.isValid());
    }

    /*public DetectorResult getQrFinder() {
        return qrFinder;
    }

    public Bitmap getRoiBitmap() {
        return roiBitmap;
    }*/
}
