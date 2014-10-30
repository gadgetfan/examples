package com.selectyour.gwtclient.rpc.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RpcServiceException extends Exception implements IsSerializable {
    public RpcServiceException() {
    }

    public RpcServiceException(String message) {
        super(message);
    }

    public RpcServiceException(Throwable cause) {
        super(cause);
    }
}
