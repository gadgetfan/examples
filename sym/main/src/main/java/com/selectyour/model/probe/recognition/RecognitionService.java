package com.selectyour.model.probe.recognition;

import java.awt.image.BufferedImage;

/**
 * Uses computer vision to recognize frame
 */
public interface RecognitionService {
    /**
     * recognizes frame for photo
     *
     * @param bufferedImage
     * @return
     */
    String recognizeWindowFrame(BufferedImage bufferedImage);
}

