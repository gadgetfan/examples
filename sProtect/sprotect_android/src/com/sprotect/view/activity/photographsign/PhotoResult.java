package com.sprotect.view.activity.photographsign;// MaiseyenkaDP gdfan 21.10.12 14:53

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.sprotect.external.zxing.SignDetectorResult;

/**
 * Stores result of FindSignActivity
 */
public class PhotoResult {
    private final Bitmap sourceSign;
    private final Long photoMethodId;

    public PhotoResult(Bitmap sourceSign, Long photoMethodId) {
        this.sourceSign = sourceSign;
        this.photoMethodId = photoMethodId;
    }

    public Bitmap getSourceSign() {
        return sourceSign;
    }

    public Long getPhotoMethod() {
        return photoMethodId;
    }
}
