package com.sprotect.io.web;

import android.graphics.Bitmap;
import com.googlecode.javacv.cpp.opencv_core;
import com.sprotect.view.activity.photographsign.PhotoResult;
import com.sprotect.model.image.ByteMatrix;

/**
 * data to upload to server
 */
public abstract class UploadData {
    private Bitmap sourceSign;
    /**
     * sign after threshold operation
     */
    private ByteMatrix thresholdSign;
    private Long photoMethodId;

    public UploadData(PhotoResult photoResult, opencv_core.IplImage thresholdSign) {
        this.sourceSign = photoResult.getSourceSign();
        this.thresholdSign = new ByteMatrix(thresholdSign.width(), thresholdSign.height(), thresholdSign.getByteBuffer());
        this.photoMethodId = photoResult.getPhotoMethod();
    }

    public String check() {
        if (sourceSign == null || sourceSign.getWidth() <= 0 || sourceSign.getHeight() <= 0) {
            return "Invalid sourceSign";
        }
        if (thresholdSign == null || thresholdSign.getWidth() != sourceSign.getWidth() || thresholdSign.getHeight() != sourceSign.getHeight()) {
            return "Invalid thresholdSign";
        }
        if (photoMethodId == null) {
            return "Invalid photoMethodId";
        }

        return "";
    }

    public Bitmap getSourceSign() {
        return sourceSign;
    }

    public ByteMatrix getThresholdSign() {
        return thresholdSign;
    }

    public Long getPhotoMethodId() {
        return photoMethodId;
    }

    public abstract TransferredData toTransferredData();

}
