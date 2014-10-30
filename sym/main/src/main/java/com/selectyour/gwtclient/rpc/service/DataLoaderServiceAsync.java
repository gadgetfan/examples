package com.selectyour.gwtclient.rpc.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataLoaderServiceAsync {
    void loadDataVolhovec(String data, AsyncCallback<Void> async);
}
