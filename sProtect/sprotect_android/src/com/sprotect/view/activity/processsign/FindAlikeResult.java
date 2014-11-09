package com.sprotect.view.activity.processsign;

import android.graphics.Bitmap;

/**
 * Result of searching sign alike current
 */
public class FindAlikeResult {
    private final String signId;
    private final Bitmap bitmap;
    private final int differencePercent;

    public FindAlikeResult(String signId, Bitmap bitmap, int differencePercent) {
        this.signId = signId;
        this.bitmap = bitmap;
        this.differencePercent = differencePercent;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getDifferencePercent() {
        return differencePercent;
    }

    public String getSignId() {
        return signId;
    }
}
