package com.selectyour.tapestry.attachments;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

import java.io.IOException;
import java.io.InputStream;

//TODO: need to understand how it works and make one root with NeverExpiredAttachment
public class AttachmentStreamResponse implements StreamResponse {
    /**
     * This can be changed to something obscure, so that it is more likely to trigger a "Save As" dialog, although there
     * is no guarantee.
     * <p/>
     * ex: application/x-download
     * <p/>
     * See http://www.onjava.com/pub/a/onjava/excerpt/jebp_3/index3.html
     */
    protected String contentType = "text/plain";
    protected String extension = "txt";
    protected String filename = "default";
    private InputStream is = null;

    public AttachmentStreamResponse(InputStream is, String filenameIn) {
        this.is = is;
        if (filenameIn != null) {
            this.filename = filenameIn;
        }
    }

    public AttachmentStreamResponse(InputStream is) {
        this.is = is;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getStream() throws IOException {
        return is;
    }

    public void prepareResponse(Response arg0) {
        arg0.setHeader("Content-Disposition", "attachment; filename=" + filename + ((extension == null) ? "" : ("." + extension)));
        arg0.setHeader("Expires", "0");
        arg0.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        arg0.setHeader("Pragma", "public");
        //We can't set the length here because we only have an Input Stream at this point. (Although we'd like to.)
        //We can only get size from an output stream.  arg0.setContentLength(.length);
    }
}
