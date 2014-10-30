package com.selectyour.tapestry.attachments.img;// MaiseyenkaDP gdfan 23.06.12 19:46

/**
 * JPEG attachment, that never will expire
 */
public class NeverExpiredJpeg extends NeverExpiredAttachment {
    public NeverExpiredJpeg(byte[] data) {
        super(data, "image/jpeg", "jpg", "default");
    }
}
