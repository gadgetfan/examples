package com.sprotect.io.web;

import com.sprotect.model.entity.photomethod.PhotoMethod;
import com.sprotect.model.entity.sign.Sign;
import com.sprotect.model.image.ByteMatrix;

import java.awt.image.BufferedImage;

/**
 * data from servlet transformed to DAO classes view
 */
public class DownloadedData {
    private final String signName;
    private final BufferedImage sourceSign;
    private final ByteMatrix thresholdSign;
    private final PhotoMethod photoMethod;

    public DownloadedData(String signName, BufferedImage sourceSign, ByteMatrix thresholdSign, PhotoMethod photoMethod) {
        this.signName = signName;
        this.sourceSign = sourceSign;
        this.thresholdSign = thresholdSign;
        this.photoMethod = photoMethod;
    }

    public ByteMatrix getThresholdSign() {
        return thresholdSign;
    }

    public PhotoMethod getPhotoMethod() {
        return photoMethod;
    }

    public String getSignName() {
        return signName;
    }

    public BufferedImage getSourceSign() {
        return sourceSign;
    }
}
