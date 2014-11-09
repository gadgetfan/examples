package com.sprotect.view.activity.photographsign.overlayview;// MaiseyenkaDP gdfan 22.09.12 18:56

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.sprotect.SProtectApplication;
import com.sprotect.external.android.javacv.JavaCvImageUtils;
import com.sprotect.external.android.zxing.ZxingImageUtils;
import com.sprotect.model.android.AndroidImageUtils;
import com.sprotect.model.camera.CameraManager;
import com.sprotect.model.camera.PictureTakenCallback;
import com.sprotect.view.activity.photographsign.PhotoResult;

import java.io.IOException;

/**
 * cut sign from picture and returns it to activity
 */
public class CutSignHandler implements PictureTakenCallback {
    private final SProtectApplication application;

    public CutSignHandler(SProtectApplication application) {
        this.application = application;
    }

    @Override
    public void onPictureTaken(byte[] data, Rect roi, CameraManager cameraManager, boolean rowData) {
        //TODO: remove rowData param
        PhotoResult photoResult = null;
        if (rowData) {
            /*int[] out = new int[cameraManager.getPreviewRect().width() * cameraManager.getPreviewRect().height()];
            AndroidImageUtils.decodeYUV(out, data, cameraManager.getPreviewRect().width(),
                    cameraManager.getPreviewRect().height());
            bitmap = Bitmap.createBitmap(out, cameraManager.getPreviewRect().width(),
                    cameraManager.getPreviewRect().height(), Bitmap.Config.ARGB_8888);*/

            // not transformed bitmap
            photoResult = new PhotoResult(ZxingImageUtils.getCroppedBitmap(data, cameraManager.getPreviewRect(), roi), 1L);

            //transformed bitmap with only qr-code
            /*DetectorResult detectorResult = ZxingImageUtils.detectQrFinders(data, cameraManager.getPreviewRect(),
                    cameraManager.getPreviewRoiRect());
            bitmap = ZxingImageUtils.bitMatrixToBitmap(detectorResult.getBits());*/

        } else {
            Bitmap srcBitmap = BitmapFactory.decodeByteArray(data, 0, data.length); //TODO: optimize bitmap cut
            srcBitmap = AndroidImageUtils.cropBitmap(srcBitmap, roi);

            //save image on disk
            /*try {
                AndroidImageUtils.saveImage(srcBitmap, "sign");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }*/

            srcBitmap = JavaCvImageUtils.processPhotoFromCamera(srcBitmap);

            photoResult = new PhotoResult(srcBitmap, 3L);//$$2L - for me, 3L - for Maxim
        }

        application.processSign(photoResult);
    }

}
