package com.selectyour.model.probe;

import com.selectyour.model.baseentity.BaseService;

/**
 * probe service
 */
public interface ProbeService extends BaseService<Probe, ProbeRepository> {
    /**
     * checks, if client has uploaded photo for probe, not using default photo
     *
     * @param probeCookie
     * @return
     */
    boolean hasUploadedPhoto(String probeCookie);

    /**
     * saves (creates or updates) probe for registered client
     *
     * @param clientId - id of client
     * @param photo
     * @return
     */
    void savePhotoForRegisteredClient(Long clientId, byte[] photo);

    /**
     * saves (creates or updates) probe for registered client
     *
     * @param probeCookie - cookie of client
     * @param photo
     * @return - cookie for saved probe
     */
    String savePhotoForUnregisteredClient(String probeCookie, byte[] photo);

    /**
     * returns probe for registered client
     *
     * @param clientId
     * @return
     */
    Probe getForRegisteredClient(Long clientId);

    /**
     * returns probe for unregistered client
     *
     * @param probeIdCookie
     * @return
     */
    Probe getForUnregisteredClient(String probeIdCookie);

    /**
     * If there is no probe with probeIdCookie, then creates it by copying default photo.
     * After this returns the new probeCookie
     *
     * @param probeCookie
     * @return
     */
    String createIfNeededAndGetForUnregisteredClient(String probeCookie);

    void deleteForUnregisteredClient(String probeIdCookie);
}

