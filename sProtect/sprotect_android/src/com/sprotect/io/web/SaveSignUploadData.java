package com.sprotect.io.web;

import com.googlecode.javacv.cpp.opencv_core;
import com.sprotect.view.activity.photographsign.PhotoResult;
import com.sprotect.model.android.AndroidImageUtils;

import java.io.ByteArrayInputStream;

/**
 * data to upload, to save sign view on server as pattern
 */
public class SaveSignUploadData extends UploadData {
    private String signName;

    public SaveSignUploadData(String signName, PhotoResult photoResult, opencv_core.IplImage thresholdSign) {
        super(photoResult, thresholdSign);
        this.signName = signName;
    }

    public String check() {
        if (signName == null || signName.trim().isEmpty()) {
            return "Invalid signName";
        }

        return super.check();
    }

    public String getSignName() {
        return signName;
    }

    public TransferredData toTransferredData() {
        TransferredData transferredData = new TransferredData(
                getSignName(),
                new ByteArrayInputStream(AndroidImageUtils.toPngByteArray(getSourceSign())),
                new ByteArrayInputStream(getThresholdSign().getBuffer()),
                getPhotoMethodId().toString()
        );

        return transferredData;
    }

}
