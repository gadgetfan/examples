package com.selectyour.tapestry.attachments.img;// MaiseyenkaDP gdfan 23.06.12 19:32

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Attachment, that never expires
 */
public class NeverExpiredAttachment implements StreamResponse {
    static private final String EXPIRE_DATE = "Tue, 21 Jun 2022 16:13:05 GMT"; //TODO: replace with something calculated automatic

    private InputStream is;
    private String contentType;
    private String extension;
    private String filename;
    private int contentLength;

    public NeverExpiredAttachment(byte[] data, String contentType, String extension, String filename) {
        this.is = new ByteArrayInputStream(data);
        this.contentType = contentType;
        this.extension = extension;
        this.filename = filename;
        this.contentLength = data.length;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getStream() throws IOException {
        return is;
    }

    public void prepareResponse(Response arg0) {
        arg0.setHeader("Content-Disposition", "attachment; filename=" + filename + ((extension == null) ? "" : ("." + extension)));
        arg0.setContentLength(contentLength);
        arg0.setHeader("Expires", EXPIRE_DATE);
        //arg0.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        //arg0.setHeader("Pragma", "public");
        //We can't set the length here because we only have an Input Stream at this point. (Although we'd like to.)
        //We can only get size from an output stream.  arg0.setContentLength(.length);
    }
}
