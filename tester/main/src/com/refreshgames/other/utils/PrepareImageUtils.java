package com.refreshgames.other.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * prepares sprite images
 */
public class PrepareImageUtils {
    //TODO: need subdirectories support and use Assets.IMAGES_DIRECTORY
    private static final String SRC_DIR = "images_prepare/bob/";
    private static final String DST_DIR = "images/bob/";


    private static void prepareImage(BufferedImage bufferedImage) {
        removeBorderLightPixels(bufferedImage);
    }

    private static void removeBorderLightPixels(BufferedImage bufferedImage) {
        for (int y = 0; y < bufferedImage.getHeight(); ++y) {
            int x = 0;
            while (x < bufferedImage.getWidth()) {
                if (checkColorOnLightness(bufferedImage, x, y)) break;
                ++x;
            }

            x = bufferedImage.getWidth() - 1;
            while (x >= 0) {
                if (checkColorOnLightness(bufferedImage, x, y)) break;
                --x;
            }

        }

        for (int x = 0; x < bufferedImage.getWidth(); ++x) {
            int y = 0;
            while (y < bufferedImage.getHeight()) {
                if (checkColorOnLightness(bufferedImage, x, y)) break;
                ++y;
            }

            y = bufferedImage.getHeight() - 1;
            while (y >= 0) {
                if (checkColorOnLightness(bufferedImage, x, y)) break;
                --y;
            }

        }

    }

    private static boolean checkColorOnLightness(BufferedImage bufferedImage, int x, int y) {
        final int MAX_COLOR_COMPONENT = 0x4F;
        final int WHITE_TRANSPARENT_COLOR = 0x00FFFFFF;

        Color color = new Color(bufferedImage.getRGB(x, y), true);
        if (color.getAlpha() != 0) {
            if ((color.getRed() + color.getGreen() + color.getBlue()) / 3 > MAX_COLOR_COMPONENT) {
                bufferedImage.setRGB(x, y, WHITE_TRANSPARENT_COLOR);
            }

            return true;
        }

        return false;
    }


    public static void main(String[] args) {
        File srcDir = new File(SRC_DIR);
        File dstDir = new File(DST_DIR);

        for (File file : srcDir.listFiles()) {
            BufferedImage bufferedImage = loadImage(file);
            prepareImage(bufferedImage);

            File dstFile = new File(dstDir, file.getName());
            saveImage(bufferedImage, dstFile);
        }
    }

    private static BufferedImage loadImage(File file) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }

    private static void saveImage(BufferedImage bufferedImage, File file) {
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
