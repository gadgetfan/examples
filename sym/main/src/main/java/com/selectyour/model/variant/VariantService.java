package com.selectyour.model.variant;

import com.selectyour.model.baseentity.BaseService;

/**
 * variant service
 */
public interface VariantService extends BaseService<Variant, VariantRepository> {
    /**
     * Delimiter in data about product
     */
    static public final String DATA_DELIMITER = ";";

    /**
     * Changes data about variant for unregistered client. Unregistered client have only one variant.
     * If there is no variant, then it will be created
     *
     * @return
     */
    Variant changeVariantForUnregisteredClient(String probeCookie, String data);

    /**
     * Adds variant for registered user. registered client can have many variants
     */
    Variant addVariantForRegisteredClient(Long clientId, String data);

    /**
     * Returns variant by probe cookie
     *
     * @param probeCookie
     * @return
     */
    Variant findForUnregisteredClient(String probeCookie);
}