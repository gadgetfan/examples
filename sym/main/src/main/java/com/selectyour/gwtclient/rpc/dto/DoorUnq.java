package com.selectyour.gwtclient.rpc.dto;// MaiseyenkaDP gdfan 26.08.12 12:19

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Ids to find a picture
 */
public class DoorUnq implements IsSerializable {
    private Long serieId;
    private Long modelId;
    private Long coveringId;
    private Long glazingId;
    private Long decorId;

    public Long getSerieId() {
        return serieId;
    }

    public void setSerieId(Long serieId) {
        this.serieId = serieId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getCoveringId() {
        return coveringId;
    }

    public void setCoveringId(Long coveringId) {
        this.coveringId = coveringId;
    }

    public Long getGlazingId() {
        return glazingId;
    }

    public void setGlazingId(Long glazingId) {
        this.glazingId = glazingId;
    }

    public Long getDecorId() {
        return decorId;
    }

    public void setDecorId(Long decorId) {
        this.decorId = decorId;
    }
}
