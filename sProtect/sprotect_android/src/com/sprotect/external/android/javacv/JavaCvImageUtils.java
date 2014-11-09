package com.sprotect.external.android.javacv;

import android.graphics.Bitmap;
import com.googlecode.javacv.cpp.opencv_core;
import com.sprotect.external.javacv.JavaCvImageUtilsBase;
import com.sprotect.model.android.AndroidImageUtils;

import java.io.IOException;

import static com.googlecode.javacv.cpp.opencv_core.*;

/**
 * Utils to process images
 */
public class JavaCvImageUtils extends JavaCvImageUtilsBase {
    public static IplImage bitmapToBgraImage(Bitmap bitmap) {
        IplImage image = IplImage.create(bitmap.getWidth(), bitmap.getHeight(), IPL_DEPTH_8U, 4); //TODO: investigate: error in android log on this string
        bitmap.copyPixelsToBuffer(image.getByteBuffer());

        return image;
    }

    public static Bitmap bgraImageToBitmap(IplImage image) {
        Bitmap bitmap = Bitmap.createBitmap(image.width(), image.height(), Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(image.getByteBuffer());

        return bitmap;
    }

    public static IplImage bitmapToRgb565Image(Bitmap bitmap) {
        IplImage image = IplImage.create(bitmap.getWidth(), bitmap.getHeight(), IPL_DEPTH_8U, 2); //TODO: investigate: error in android log on this string
        bitmap.copyPixelsToBuffer(image.getByteBuffer());

        return image;
    }

    public static Bitmap rgbImageToBitmap(IplImage image) {
        Bitmap bitmap = Bitmap.createBitmap(image.width(), image.height(), Bitmap.Config.RGB_565);
        bitmap.copyPixelsFromBuffer(image.getByteBuffer());

        return bitmap;
    }

    public static void saveGrayImage(IplImage grayImage, String fileName) throws IOException {
        IplImage bgraImage = grayToBgraImage(grayImage);
        AndroidImageUtils.saveImage(bgraImageToBitmap(bgraImage), fileName);
    }

    /**
     * processing photo from camera see text
     * @param sourceSign
     * @return
     */
    public static Bitmap processPhotoFromCamera(Bitmap sourceSign) {
        //TODO: optimize
        //processing for android 2 version
        /*opencv_core.IplImage srcImage = JavaCvImageUtils.bitmapToRgb565Image(sourceSign);
        opencv_core.IplImage src4cImage = cvCreateImage(cvGetSize(srcImage), srcImage.depth(), 4);
        JavaCvImageUtils.rgb565ToArgb8888(srcImage, src4cImage);
        opencv_core.IplImage smoothImage = JavaCvImageUtils.cloneImage(src4cImage);
        JavaCvImageUtils.smoothImage(src4cImage, smoothImage);*/

        //processing for Android 4 version
        //TODO: need general procesing and better on server, not on mobile for compatibility
        opencv_core.IplImage srcImage = JavaCvImageUtils.bitmapToBgraImage(sourceSign);
        opencv_core.IplImage smoothImage = JavaCvImageUtils.cloneImage(srcImage);
        JavaCvImageUtils.smoothImage(srcImage, smoothImage);

        opencv_core.IplImage grayImage = cvCreateImage(cvGetSize(srcImage), srcImage.depth(), 1);

        JavaCvImageUtils.createGrayImageBGRA(smoothImage, grayImage);

        opencv_core.IplImage gray4cImage = JavaCvImageUtils.grayToBgraImage(grayImage);
        Bitmap grayBitmap = JavaCvImageUtils.bgraImageToBitmap(gray4cImage);

        return grayBitmap;
    }

}
