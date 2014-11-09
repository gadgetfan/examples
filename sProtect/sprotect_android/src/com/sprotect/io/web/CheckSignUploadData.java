package com.sprotect.io.web;

import com.googlecode.javacv.cpp.opencv_core;
import com.sprotect.view.activity.photographsign.PhotoResult;
import com.sprotect.model.android.AndroidImageUtils;

import java.io.ByteArrayInputStream;

/**
 * data to upload, to check is sign view legal
 */
public class CheckSignUploadData extends UploadData {
    public CheckSignUploadData(PhotoResult photoResult, opencv_core.IplImage thresholdSign) {
        super(photoResult, thresholdSign);
    }

    public TransferredData toTransferredData() {
        TransferredData transferredData = new TransferredData(
                null,
                new ByteArrayInputStream(AndroidImageUtils.toPngByteArray(getSourceSign())),
                new ByteArrayInputStream(getThresholdSign().getBuffer()),
                getPhotoMethodId().toString()
        );

        return transferredData;
    }

}
