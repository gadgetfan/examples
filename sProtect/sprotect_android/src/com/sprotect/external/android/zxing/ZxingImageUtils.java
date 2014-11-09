package com.sprotect.external.android.zxing;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.detector.Detector;
import com.sprotect.external.zxing.SignDetectorResult;

/**
 * utils for library zxing
 */
public class ZxingImageUtils {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public static SignDetectorResult detectQrFinders(final byte[] data, final Rect fullRect, Rect roiRect) {
        LuminanceSource source = getLuminanceSource(data, fullRect, roiRect);

        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            SignDetectorResult detectorResult = new SignDetectorResult(new Detector(bitmap.getBlackMatrix()).detect(null));

            return detectorResult;
        } catch (NotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FormatException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    private static PlanarYUVLuminanceSource getLuminanceSource(byte[] data, Rect fullRect, Rect roi) {
        return new PlanarYUVLuminanceSource(data, fullRect.width(), fullRect.height(),
                roi.left, roi.top, roi.width(), roi.height(), false);
    }

    public static Bitmap getCroppedBitmap(final byte[] data, final Rect fullRect, Rect roi) {
        PlanarYUVLuminanceSource source = getLuminanceSource(data, fullRect, roi);

        return source.renderCroppedGreyscaleBitmap();
    }

    public static Bitmap bitMatrixToBitmap(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, (bitMatrix.get(x, y) ? BLACK : WHITE));
            }
        }

        return bitmap;
    }
}
