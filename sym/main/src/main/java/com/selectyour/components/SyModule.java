package com.selectyour.components;// MaiseyenkaDP gdfan 30.07.12 22:20

import com.selectyour.model.mail.MailService;
import com.selectyour.pages.doors.Thanks;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.io.IOException;

/**
 * wrapper for SelectYourModuleNew
 */
public class SyModule {
    @Inject
    MailService mailService;
    /**
     * url to client's photo
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.ASSET, required = true)
    private String clientPhoto;
    /**
     * Data with coorfinates of window frames
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
    private String windowFrameData;
    /**
     * If showPicturesOnly == true, then no title are shown and no text columns with description
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String isDemo = "false";
    @Persist//TODO: remove
    @Property
    private String customerName;
    @Persist//TODO: remove
    @Property
    private String customerPhone;
    private String pictureId;

    public Object onSubmitFromBuyPicture() throws IOException {
        mailService.sendPictureOrder(String.format("Имя: %s\nТелефон: %s\nКартина: %s", customerName, customerPhone, pictureId));

        return Thanks.class;

        //try save file
        /*byte[] imageData = Base64Util.decode(resultImageData);
        Picture picture = pictureService.findOne(Long.valueOf(getPictureId()));
        if (picture != null) {
            //TODO: use lazy initialization for Door entity to get all names with picture.getStyle().getName()
            PictureDto pictureDto = pictureService.toPictureInfo(picture);
            pictureDto.setStyle(styleService.findOne(picture.getStyleId()).getName()); //TODO: arch set style in pictureService.toPictureInfo ?

            // Create PDF
            InputStream is = (new VariantInfoPdfGenerator(requestGlobals.getHTTPServletRequest().getRealPath("/"),
                    new VariantInfoReportData(imageData, pictureDto)))
                    .generatePDF();
            // Return response
            return new PdfStreamResponse(is, PDF_FILE_NAME); //TODO: who should do is.close ?
        } else {
            return null;
        }*/
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

}
