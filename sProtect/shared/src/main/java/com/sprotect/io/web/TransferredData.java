package com.sprotect.io.web;

import java.io.IOException;
import java.io.InputStream;

/**
 * data to transfer through http
 */
public class TransferredData {
    private final String signName;
    private final InputStream sourceSign;
    private final InputStream thresholdSign;
    private final String photoMethodId;

    public TransferredData(String signName, InputStream sourceSign, InputStream thresholdSign, String photoMethodId) {
        this.signName = signName;
        this.sourceSign = sourceSign;
        this.thresholdSign = thresholdSign;
        this.photoMethodId = photoMethodId;
    }

    public String getSignName() {
        return signName;
    }

    public InputStream getSourceSign() {
        return sourceSign;
    }

    public InputStream getThresholdSign() {
        return thresholdSign;
    }

    public String getPhotoMethodId() {
        return photoMethodId;
    }

    public void closeStreams() throws IOException {
        sourceSign.close();
        thresholdSign.close();
    }
}
