package com.selectyour.tapestry.attachments.img;

import com.selectyour.tapestry.attachments.AttachmentStreamResponse;

import java.io.InputStream;

public class JpegStreamResponse extends AttachmentStreamResponse {
    public JpegStreamResponse(InputStream is, String args) {
        super(is, args);
        this.contentType = "image/jpeg"; //TODO: I use it to return png-images too. Set right content type
        this.extension = "jpg";
    }

    public JpegStreamResponse(InputStream is) {
        super(is);
        this.contentType = "image/jpeg";
        this.extension = "jpg";
    }
}