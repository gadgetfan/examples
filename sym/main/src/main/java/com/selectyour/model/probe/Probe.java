package com.selectyour.model.probe;

import com.selectyour.model.ModelConstants;
import com.selectyour.model.baseentity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * Probe - entity, if customer tries to select a product
 */
@Entity
public class Probe extends BaseEntity {
    /**
     * no window frame data
     */
    static public final String NO_WINDOW_FRAME_DATA = "";
    @Column(nullable = false, length = ModelConstants.MAX_VARCHAR_LENGTH)
    private String windowFrame = NO_WINDOW_FRAME_DATA;
    /**
     * customers photo
     */
    @Column(nullable = false)
    @Lob
    private byte[] sourcePhoto;
    @Column(nullable = true) //nullable = true to save probe for unregistered clients
    private Long clientId;

    public byte[] getSourcePhoto() {
        return sourcePhoto;
    }

    public void setSourcePhoto(byte[] data) {
        this.sourcePhoto = data;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getWindowFrame() {
        return windowFrame;
    }

    public void setWindowFrame(String windowFrame) {
        this.windowFrame = windowFrame;
    }
}

