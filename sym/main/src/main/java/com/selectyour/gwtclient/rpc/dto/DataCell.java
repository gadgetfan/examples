package com.selectyour.gwtclient.rpc.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * cell with data
 */
public class DataCell implements IsSerializable {
    private Long[] selectIds;
    private PictureDto pictureDto;

    public DataCell() {
    }

    public DataCell(Long[] selectIds, PictureDto pictureDto) {
        this.selectIds = selectIds;
        this.pictureDto = pictureDto;
    }

    public String getImageUrl() {
        return pictureDto.getImageUrl();
    }

    public String getName() {
        return pictureDto.getName();
    }

    public Long[] getSelectIds() {
        return selectIds;
    }

    public String getDescription() {
        return pictureDto.getDescription();
    }

    public PictureDto getPictureDto() {
        return pictureDto;
    }
}
