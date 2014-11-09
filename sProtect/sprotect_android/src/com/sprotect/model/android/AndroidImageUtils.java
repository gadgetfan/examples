package com.sprotect.model.android;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ImageUtils to convert images
 */
public class AndroidImageUtils {
    // decode Y, U, and V values on the YUV 420 buffer described as YCbCr_422_SP by Android
    // David Manpearl 081201
    public static void decodeYUV(int[] out, byte[] fg, int width, int height)
            throws NullPointerException, IllegalArgumentException {
        final int sz = width * height;
        if (out == null) throw new NullPointerException("buffer 'out' is null");
        if (out.length < sz) throw new IllegalArgumentException("buffer 'out' size " + out.length + " < minimum " + sz);
        if (fg == null) throw new NullPointerException("buffer 'fg' is null");
        if (fg.length < sz)
            throw new IllegalArgumentException("buffer 'fg' size " + fg.length + " < minimum " + sz * 3 / 2);
        int i, j;
        int Y, Cr = 0, Cb = 0;
        for (j = 0; j < height; j++) {
            int pixPtr = j * width;
            final int jDiv2 = j >> 1;
            for (i = 0; i < width; i++) {
                Y = fg[pixPtr];
                if (Y < 0) Y += 255;
                if ((i & 0x1) != 1) {
                    final int cOff = sz + jDiv2 * width + (i >> 1) * 2;
                    Cb = fg[cOff];
                    if (Cb < 0) Cb += 127;
                    else Cb -= 128;
                    Cr = fg[cOff + 1];
                    if (Cr < 0) Cr += 127;
                    else Cr -= 128;
                }
                int R = Y + Cr + (Cr >> 2) + (Cr >> 3) + (Cr >> 5);
                if (R < 0) R = 0;
                else if (R > 255) R = 255;
                int G = Y - (Cb >> 2) + (Cb >> 4) + (Cb >> 5) - (Cr >> 1) + (Cr >>
                        3) + (Cr >> 4) + (Cr >> 5);
                if (G < 0) G = 0;
                else if (G > 255) G = 255;
                int B = Y + Cb + (Cb >> 1) + (Cb >> 2) + (Cb >> 6);
                if (B < 0) B = 0;
                else if (B > 255) B = 255;
                out[pixPtr++] = 0xff000000 + (B << 16) + (G << 8) + R;
            }
        }
    }

    public static int[] decodeYUV420SP(byte[] yuv420sp, int width, int height) {

        final int frameSize = width * height;

        int rgb[] = new int[width * height];
        for (int j = 0, yp = 0; j < height; j++) {
            int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
            for (int i = 0; i < width; i++, yp++) {
                int y = (0xff & ((int) yuv420sp[yp])) - 16;
                if (y < 0) y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & yuv420sp[uvp++]) - 128;
                    u = (0xff & yuv420sp[uvp++]) - 128;
                }

                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0) r = 0;
                else if (r > 262143) r = 262143;
                if (g < 0) g = 0;
                else if (g > 262143) g = 262143;
                if (b < 0) b = 0;
                else if (b > 262143) b = 262143;

                rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) &
                        0xff00) | ((b >> 10) & 0xff);


            }
        }
        return rgb;
    }

    public static Bitmap cropBitmap(Bitmap bitmap, Rect pictureRoiRect) {
        assert (pictureRoiRect.right <= bitmap.getWidth() && pictureRoiRect.bottom <= bitmap.getHeight());

        // calculate the scale - in this case = 0.4f
        /*float scaleWidth = ((float) pictureRoiRect.width()) / bitmap.getWidth();
        float scaleHeight = ((float) pictureRoiRect.height()) / bitmap.getHeight();

        // createa matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);*/

        // recreate the new Bitmap
        // The following line is the place where you can crop the image.
        // If you do not want to resize image then in above line keep scaledWidth and
        //  scaledHeight to 1.
        // Then in te following line, you can change following four parameters 0,0,width,height
        // Thease four parameters mark the four axis readings of bounding box. Change it and
        // see how it works

        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, pictureRoiRect.left, pictureRoiRect.top,
                pictureRoiRect.width(), pictureRoiRect.height());

        return cropBitmap;
    }

    public static String saveImage(Bitmap bitmap, String name) throws IOException {
        //get image data
        byte[] bitmapArray = toPngByteArray(bitmap);

        //get file
        File dstFile = new File(Environment.getExternalStorageDirectory() + File.separator + name + ".png");

        //save image data to file
        dstFile.createNewFile();
        //write the bytes in file
        FileOutputStream fo = new FileOutputStream(dstFile);
        fo.write(bitmapArray);
        fo.close();

        return dstFile.getAbsolutePath();
    }

    public static byte[] toPngByteArray(Bitmap bitmap) {
        ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageBytes);
        return imageBytes.toByteArray();
    }
}
