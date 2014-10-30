package com.selectyour.gwtclient.rpc.service;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * service to load data about doors from site
 */

public interface DataLoaderService extends RemoteService {
    /**
     * loads data and returns error (if exists)
     *
     * @param data - data to load
     */
    //void loadDataVolhovec(String data) throws DataLoaderException;
}
