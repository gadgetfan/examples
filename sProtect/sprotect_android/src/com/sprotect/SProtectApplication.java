package com.sprotect;// MaiseyenkaDP gdfan 22.09.12 18:49

import android.app.Application;
import android.content.Intent;
import com.googlecode.javacv.cpp.opencv_core;
import com.sprotect.model.android.AndroidImageUtils;
import com.sprotect.view.activity.niveasign.NiveaProductActivity;
import com.sprotect.view.activity.photographsign.PhotoResult;
import com.sprotect.view.activity.processsign.ProcessSignActivity;
import com.sprotect.io.web.CheckSignUploadData;
import com.sprotect.io.web.DataUploader;
import com.sprotect.io.web.SaveSignUploadData;

import java.io.IOException;

/**
 * application, is used to save data between activities
 */
public class SProtectApplication extends Application {
    private PhotoResult photoResult;
    private String signId;

    public PhotoResult getPhotoResult() {
        return photoResult;
    }

    public void processSign(PhotoResult photoResult) {
        this.photoResult = photoResult;

        Intent intent;
        if (!getResources().getBoolean(R.bool.showNivea)) {
            intent = new Intent(getBaseContext(), ProcessSignActivity.class);
        } else {
            intent = new Intent(getBaseContext(), NiveaProductActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public String saveSign(opencv_core.IplImage thresholdSign) throws IOException {
        return DataUploader.uploadDataToServer(getResources().getString(R.string.saveSignUrl),
                new SaveSignUploadData(signId, photoResult, thresholdSign));
    }

    public String saveSignOnCard() throws IOException {
            String fileName = AndroidImageUtils.saveImage(photoResult.getSourceSign(), getSignId());

            return fileName;
        }
    public String checkSign(opencv_core.IplImage thresholdSign) throws IOException {
        return DataUploader.uploadDataToServer(getResources().getString(R.string.checkSignUrl),
                new CheckSignUploadData(photoResult, thresholdSign));
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }
}
