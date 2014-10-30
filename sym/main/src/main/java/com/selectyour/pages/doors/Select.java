package com.selectyour.pages.doors;// MaiseyenkaDP gdfan 07.07.12 9:07

import com.selectyour.pages.BaseComponent;

/**
 * Page with site installed in component
 */
public class Select extends BaseComponent {
    static private final String PDF_FILE_NAME = "picture";

    /*@Inject
    PictureService pictureService;
    @Inject
    StyleService styleService;
    @Inject

    @InjectPage
    private VariantInfo variantInfo;

    @Inject
    @Service("RequestGlobals")
    private RequestGlobals requestGlobals;

    private String productData;

    private String resultImageData;*/

    /*Object onSuccessFromShowVariant() {
        Variant variant = null;
        if (isRegisteredClient()) {
            variant = variantService.addVariantForRegisteredClient(getSession().getClientId(), productData);
            variantInfo.setVariantId(variant.getId());
        } else {
            //create new probe, if needed
            String probeCookie = probeService.createIfNeededAndGetForUnregisteredClient(getProbeIdCookie());
            setProbeIdCookie(probeCookie);
            variant = variantService.changeVariantForUnregisteredClient(probeCookie, productData);
        }

        return variantInfo;
    }

    Object onSuccessFromChangePhoto() {
        return ChangePhoto.class;
    }

    public String getProductData() {
        return productData;
    }

    public void setProductData(String productData) {
        this.productData = productData;
    }

    public String getResultImageData() {
        return resultImageData;
    }

    public void setResultImageData(String resultImageData) {
        this.resultImageData = resultImageData;
    }*/

}
