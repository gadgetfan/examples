package com.selectyour.gwtshared;

import com.google.gwt.i18n.client.NumberFormat;

public class PictureConstants {
    public static final double MARKER_WIDTH = 18.3;
    public static final double MARKER_HEIGHT = 18.4;
    public static final String DOMAIN_NAME = "selectartforyourself.com";
    //TODO: load data from properties file for needed language
    private static final NumberFormat SIZE_FORMAT = NumberFormat.getFormat("#.#");

    public static String formatStyle(String style) {
        return "Стиль: " + style;
    }

    public static String formatPrice(String price) {
        return "Цена: " + price;
    }

    public static String formatSize(double width, double height) {
        return "Размер, см: " + PictureConstants.SIZE_FORMAT.format(width) + "x"
                + PictureConstants.SIZE_FORMAT.format(height);
    }
}
