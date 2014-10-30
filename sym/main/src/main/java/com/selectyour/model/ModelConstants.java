package com.selectyour.model;// MaiseyenkaDP gdfan 17.06.12 13:37

/**
 * Constants for model
 */
public class ModelConstants {
    /**
     * max length of varchar field
     */
    static public final int MAX_VARCHAR_LENGTH = 5000; //(30000 / 6) ~ (32767 bytes (most RDBS) / 6 bytes (UTF-8 encoding))

    /**
     * fetch all rows from query
     */
    static public final int FETCH_ALL_ROWS = 1000;
}
