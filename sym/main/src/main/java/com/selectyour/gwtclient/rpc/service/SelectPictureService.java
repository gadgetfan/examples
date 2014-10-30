package com.selectyour.gwtclient.rpc.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.selectyour.gwtclient.rpc.dto.DataRow;
import com.selectyour.gwtclient.rpc.dto.PictureDto;
import com.selectyour.gwtclient.rpc.exception.SelectDoorException;

import java.util.List;

/**
 * service provides data, needed client to select picture
 */

public interface SelectPictureService extends RemoteService {
    /**
     * returns data for Select Style stage
     *
     * @param maxDataCells
     * @return
     */
    List<DataRow> getSelectStyleData(int maxDataCells);

    /**
     * returns data for select Picture stage
     *
     * @param selectIds
     * @return
     */
    List<DataRow> getSelectPictureData(Long[] selectIds, int maxDataCells);


    /**
     * returns picture by Ids
     *
     * @param pictureId
     * @return
     */
    PictureDto getPictureInfo(Long pictureId) throws SelectDoorException;

}
