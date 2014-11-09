package com.sprotect;

/**
 * Shared between modules constants
 */
public class SharedConstants {
    /**************************Data Transfer**************************/
    /**
     * name of part in multipart request with sign (file) data
     */
    public static final String HTTP_SOURCE_SIGN_PART_NAME = "sign";

    /**
     * name of part in multipart request: id of sign
     */
    public static final String HTTP_SIGN_ID_PART_NAME = "signName";

    /**
     * name of part in multipart request: sign after adaptive threshold
     */
    public static final String HTTP_THRESHOLD_SIGN_PART_NAME = "threshold";

    /**
     * name of part in multipart request: name for photoMethod id
     */
    public static final String HTTP_PHOTO_METHOD_ID_PART_NAME = "pmId";

    /*****************************Sparkle detecting************************/

    public static final int QR_CODE_DIMENSION = 29;
}
