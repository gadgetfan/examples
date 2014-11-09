package com.sprotect.external.zxing;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.detector.Detector;

import java.awt.image.BufferedImage;

/**
 * utils for library zxing
 */
public class ZxingImageUtils {
    public static SignDetectorResult detectQrFinders(BufferedImage image) {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            DetectorResult detectorResult = new Detector(bitmap.getBlackMatrix()).detect(null);

            return new SignDetectorResult(detectorResult);
        } catch (NotFoundException ignore) {
            ignore.printStackTrace();
        } catch (FormatException ignore) {
            ignore.printStackTrace();
        }

        return null;
    }


    public static BufferedImage fetchDetectorImage(BufferedImage image) {
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);

        return source.getImage();
    }
}
