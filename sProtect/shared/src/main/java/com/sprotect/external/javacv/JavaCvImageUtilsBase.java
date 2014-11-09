package com.sprotect.external.javacv;

import com.googlecode.javacv.cpp.opencv_core;
import com.sprotect.model.image.ByteMatrix;

import java.util.List;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;

/**
 * Utils to process images
 */
public class JavaCvImageUtilsBase {
    public static IplImage cloneImage(IplImage sourceImage) {
        return opencv_core.cvCloneImage(sourceImage);
    }

/*
    public static void createGrayImage(IplImage sourceImage, IplImage grayImage) {
        cvCvtColor(sourceImage, grayImage, CV_BGR2GRAY);
    }
*/

    public static void createGrayImageBGRA(IplImage bgraImage, IplImage grayImage) {
        cvCvtColor(bgraImage, grayImage, CV_BGRA2GRAY);
    }

    public static void createGrayImageRgb565(IplImage rgbImage, IplImage grayImage) {
        cvCvtColor(rgbImage, grayImage, CV_BGR5652GRAY);
    }

    public static void rgb565ToArgb8888(IplImage rgbImage, IplImage argbImage) {
        cvCvtColor(rgbImage, argbImage, CV_BGR5652BGRA);
    }

    public static IplImage grayToBgraImage(IplImage grayImage) {
        IplImage bgraImage = cvCreateImage(cvGetSize(grayImage), grayImage.depth(), 4);

        cvCvtColor(grayImage, bgraImage, CV_GRAY2BGRA);

        /*// Alpha channel creation (transparency)
        IplImage alphaChannel = cvCreateImage(cvGetSize(grayImage), grayImage.depth(), 1);
        // Set the alpha value
        cvSet(alphaChannel, CvScalar.ALPHA255);

        // Merge the 4 channel to an BGRA image
        cvMerge(grayImage, grayImage, grayImage, alphaChannel, bgraImage);*/

        return bgraImage;
    }

    public static void smoothImage(IplImage sourceImage, IplImage smoothImage) {
        cvSmooth(sourceImage, smoothImage, CV_GAUSSIAN, 5, 5, 0, 0);//TODO: for better result, choose params
    }

    public static void adaptiveThresholdImage(IplImage sourceImage, IplImage thresholdImage) {
        cvAdaptiveThreshold(sourceImage, thresholdImage, 255, CV_ADAPTIVE_THRESH_GAUSSIAN_C, CV_THRESH_BINARY, 15, -5);//TODO: for better result, choose better params
    }

    public static void fillBlack(IplImage image) {
        cvSet(image, opencv_core.CvScalar.BLACK);
    }

    public static void drawSparkles(IplImage image, List<float[]> sparkleList) {
        drawSparkles(image, sparkleList, 1, 0, 0);
    }

    public static void drawSparkles(IplImage image, List<float[]> sparkleList, float k, float dx, float dy) {
        ByteMatrix matrix = new ByteMatrix(image.width(), image.height(), image.getByteBuffer());
        for (float[] sparkle : sparkleList) {
            for (int i = 0; i < sparkle.length; i += 2) {
                matrix.setColor(calcCoordinate(sparkle[i], k, dx),
                        calcCoordinate(sparkle[i + 1], k, dy), (byte) 127);
            }
        }
    }

    private static int calcCoordinate(float v, float k, float dx) {
        return Math.round(v * k + dx);
    }

}
