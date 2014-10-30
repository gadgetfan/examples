package com.selectyour.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Prepares pictures:
 * 1. removes incorrect pixels on the border: transparent-nontransparent pixel
 */
public class PicturePreparer {

    public static final String PATH_TO_PICTURES = "d:/dev/data/Dropbox/projects/sym/sym/main/src/main/webapp/db/new/picture/";

    static public void main(String[] args) throws IOException {

        processDirectory(PATH_TO_PICTURES, 2);
        processDirectory(PATH_TO_PICTURES + "/s", 1);
    }

    private static void processDirectory(String dir, int increaseSize) throws IOException {
        File directory = new File(dir);
        PicturePreparer picturePreparer = new PicturePreparer();
        int i = 0;
        for (File imageFile : directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".gif");
            }
        })) {
            ++i;
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            BufferedImage resultImage = picturePreparer.increaseBorder(bufferedImage, increaseSize);
            ImageIO.write(resultImage, "GIF", imageFile);
        }

        System.out.println(String.format("%d files were processed", i));
    }

    private BufferedImage increaseBorder(BufferedImage image, int increaseSize) {
        BufferedImage resultImage = AwtImageUtils.clone(image);
        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                int argb = image.getRGB(x, y);
                if ((argb & 0xFF000000) == 0) {
                    for (int tx = Math.max(0, x - increaseSize); tx < Math.min(image.getWidth(), x + increaseSize + 1); ++tx) {
                        for (int ty = Math.max(0, y - increaseSize); ty < Math.min(image.getHeight(), y + increaseSize + 1); ++ty) {
                            resultImage.setRGB(tx, ty, 0x00FFFFFF);
                        }
                    }
                }
            }
        }
        return resultImage;
    }

}
