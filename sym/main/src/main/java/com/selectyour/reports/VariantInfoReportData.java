package com.selectyour.reports;

import com.selectyour.gwtclient.rpc.dto.PictureDto;
import com.selectyour.gwtshared.PictureConstants;

/**
 * data to create variant info report
 */
public class VariantInfoReportData {
    //TODO: remove string data in properties
    static private final String ADS_INFO = "Картины подобраны на сайте www." + PictureConstants.DOMAIN_NAME;
    static private final String[] OTHER_INFO = new String[]{
            ""
    };

    private byte[] imageData;
    private String price;
    private String ads;
    private String[] description;
    private String[] other;

    public VariantInfoReportData(byte[] imageData, PictureDto pictureDto) {
        //TODO: use doorInfo instaed of price and description
        this.imageData = imageData;
        this.price = pictureDto.getPrice();
        this.ads = ADS_INFO;
        this.description = new String[]{
                PictureConstants.formatStyle(pictureDto.getStyle())
        };
        this.other = OTHER_INFO;
    }

    //TODO: remove this constructor
    public VariantInfoReportData(byte[] imageData, String price, String[] description) {
        this.imageData = imageData;
        this.price = price;
        this.ads = ADS_INFO;
        this.description = description;
        this.other = OTHER_INFO;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public String getPrice() {
        return price;
    }

    public String getAds() {
        return ads;
    }

    public String[] getDescription() {
        return description;
    }

    public String[] getOther() {
        return other;
    }
}
