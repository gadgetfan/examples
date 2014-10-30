package com.selectyour.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;

/**
 * base image utils for BufferedImage
 */
public class AwtImageUtils {

    public static BufferedImage toBufferedImage(byte[] photo) {
        InputStream in = new ByteArrayInputStream(photo);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(in);
        } catch (IOException e) {
            //TODO: arch add log;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bufferedImage;
    }

    public static byte[] toByteArray(BufferedImage bufferedImage) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream() {
            @Override
            public synchronized byte[] toByteArray() {
                return this.buf;
            }
        };
        try {
            ImageIO.write(bufferedImage, "jpeg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: arch add log

            return null;
        }

        return outputStream.toByteArray();
    }

    public static BufferedImage loadFrom(String fileName) {
        BufferedImage bufferedImage = null;
        File file = new File(fileName);
        if (!file.exists()) {
            //new AssertException(null, "Cannot find file %s", fileName);
        } else {
            try {
                bufferedImage = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bufferedImage;
    }

    public static BufferedImage clone(BufferedImage srcImage) {
        ColorModel cm = srcImage.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = srcImage.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static void drawOval(Graphics2D g2d, float x, float y, int radius) {
        Shape theCircle = new Ellipse2D.Double(x - radius, y - radius, 2.0 * radius, 2.0 * radius);
        g2d.draw(theCircle);
    }

    public static InputStream toInputStream(BufferedImage image) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream() {
            @Override
            public synchronized byte[] toByteArray() {
                return this.buf;
            }
        };
        ImageIO.write(image, "png", outputStream);

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray(), 0, outputStream.size());
        outputStream.close();

        return inputStream;
    }


    /**
     * transforms fileInputStream into BufferedImage. Closes fileStream
     *
     * @param fileStream
     * @return
     */
    public static BufferedImage toBufferedImage(InputStream fileStream) throws IOException {
        BufferedImage image = ImageIO.read(fileStream);
        fileStream.close();

        return image;
    }
}
