package com.sprotect.model.image;

import com.sprotect.external.zxing.SignDetectorResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * base image utils for BufferedImage
 */
public class AwtImageUtils {
    public static BufferedImage loadFrom(String fileName) {
        BufferedImage bufferedImage = null;
        URL url = ClassLoader.getSystemResource(fileName);
        if (url == null) {
            System.out.println("Cannot find file " + fileName);
        } else {
            try {
                bufferedImage = ImageIO.read(url);
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
     * @param fileStream
     * @return
     */
    public static BufferedImage toBufferedImage(InputStream fileStream) throws IOException {
        BufferedImage image = ImageIO.read(fileStream);
        fileStream.close();

        return image;
    }

    public static void drawFinders(BufferedImage sign, SignDetectorResult signDetectorResult) {
        Graphics2D g2d = sign.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.drawLine(Math.round(signDetectorResult.getTopLeft().getX()), Math.round(signDetectorResult.getTopLeft().getY()),
                Math.round(signDetectorResult.getTopRight().getX()), Math.round(signDetectorResult.getTopRight().getY()));
        g2d.drawLine(Math.round(signDetectorResult.getTopRight().getX()), Math.round(signDetectorResult.getTopRight().getY()),
                Math.round(signDetectorResult.getAlignmentPattern().getX()), Math.round(signDetectorResult.getAlignmentPattern().getY()));
        g2d.drawLine(Math.round(signDetectorResult.getAlignmentPattern().getX()), Math.round(signDetectorResult.getAlignmentPattern().getY()),
                Math.round(signDetectorResult.getBottomLeft().getX()), Math.round(signDetectorResult.getBottomLeft().getY()));
        g2d.drawLine(Math.round(signDetectorResult.getBottomLeft().getX()), Math.round(signDetectorResult.getBottomLeft().getY()),
                Math.round(signDetectorResult.getTopLeft().getX()), Math.round(signDetectorResult.getTopLeft().getY()));

        g2d.dispose();
    }
}
