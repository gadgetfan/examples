package com.selectyour.model.mail;

import java.io.IOException;

/**
 * Sends emails
 */
public interface MailService {
    void sendPictureOrder(String content) throws IOException;

    void sendVisualizationOrder(String content) throws IOException;
}
