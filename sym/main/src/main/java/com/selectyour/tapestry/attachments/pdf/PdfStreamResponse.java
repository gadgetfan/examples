package com.selectyour.tapestry.attachments.pdf;

import com.selectyour.tapestry.attachments.AttachmentStreamResponse;

import java.io.InputStream;

/**
 * Stream response for pdf document
 */
public class PdfStreamResponse extends AttachmentStreamResponse {
    public PdfStreamResponse(InputStream is, String args) {
        super(is, args);
        this.contentType = "application/pdf; charset=UTF-8";
        this.extension = "pdf";
    }

    public PdfStreamResponse(InputStream is) {
        super(is);
        this.contentType = "application/pdf; charset=UTF-8";
        this.extension = "pdf";
    }
}