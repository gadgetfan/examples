package com.selectyour.components;// MaiseyenkaDP gdfan 30.07.12 22:20

import com.selectyour.model.mail.MailService;
import com.selectyour.pages.doors.Thanks;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.io.IOException;

/**
 * Form to order visualization: photo with marker
 */
public class VisualizationOrder {
    @Inject
    MailService mailService;
    //@Persist//TODO: arch
    //$$remove @Persist in SyModule too
    @Property
    private String customerName;
    //@Persist//TODO: arch remove
    @Property
    private String customerPhone;

    public Object onSubmitFromVisualizationOrder() throws IOException {
        mailService.sendVisualizationOrder(String.format("Имя: %s\nТелефон: %s", customerName, customerPhone));

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
}
