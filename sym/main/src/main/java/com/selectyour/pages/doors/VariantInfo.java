package com.selectyour.pages.doors;

import com.selectyour.model.variant.Variant;
import com.selectyour.model.variant.VariantService;
import com.selectyour.pages.BaseComponent;
import com.selectyour.reports.VariantInfoPdfGenerator;
import com.selectyour.reports.VariantInfoReportData;
import com.selectyour.tapestry.attachments.pdf.PdfStreamResponse;
import com.selectyour.utils.Base64Util;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Service;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import java.io.InputStream;

/**
 * page with info about variant of product for client
 */
public class VariantInfo extends BaseComponent {
    /**
     * name of pdf-document with info about variant
     */
    static private final String PDF_FILE_NAME = "picture";

    private Long variantId;
    private Variant variant;
    private String descriptionRow;
    private String resultImageData;

    @Inject
    @Service("RequestGlobals")
    private RequestGlobals requestGlobals;

    @Inject
    private VariantService variantService;

    void onActivate(Long variantId) {//is used for registered clients only
        if (isRegisteredClient()) {
            //variant = variantService.findOne(variantId);
            //TODO: need to CHECK, if this variant belongs to this client
        }
    }

    void onActivate() {//is used for unregistered clients only
        if (!isRegisteredClient()) {
            variant = variantService.findForUnregisteredClient(getProbeIdCookie());
        }
    }

    Long onPassivate() {//is used for registered clients only
        /*if (isRegisteredClient()) {
            return variantId;
        }*/
        return null;
    }

    public StreamResponse onSubmitFromSaveInfo() {
        //try save file
        byte[] imageData = Base64Util.decode(resultImageData);

        // Create PDF
        InputStream is = (new VariantInfoPdfGenerator(requestGlobals.getHTTPServletRequest().getRealPath("/"),
                new VariantInfoReportData(
                        imageData,
                        variant.getPrice(),
                        getDescriptionArray()
                ))).generatePDF();
        // Return response
        return new PdfStreamResponse(is, PDF_FILE_NAME); //TODO: who should do is.close ?
    }

    void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public Variant getVariant() {
        return variant;
    }

    public String[] getDescriptionArray() {
        return variant.getDescription().split(VariantService.DATA_DELIMITER);
    }

    public String getDescriptionRow() {
        return descriptionRow;
    }

    public void setDescriptionRow(String descriptionRow) {
        this.descriptionRow = descriptionRow;
    }

    public String getResultImageData() {
        return resultImageData;
    }

    public void setResultImageData(String resultImageData) {
        this.resultImageData = resultImageData;
    }
}
