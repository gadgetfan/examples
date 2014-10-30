package com.selectyour.gwtclient.rpc.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.selectyour.gwtclient.rpc.dto.DataRow;
import com.selectyour.gwtclient.rpc.dto.PictureDto;

import java.util.List;

public interface SelectPictureServiceAsync {

    void getSelectStyleData(int maxDataCells, AsyncCallback<List<DataRow>> async);

    void getSelectPictureData(Long[] selectIds, int maxDataCells, AsyncCallback<List<DataRow>> async);

    void getPictureInfo(Long pictureId, AsyncCallback<PictureDto> async);

}
