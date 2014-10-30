package com.selectyour.gwtclient.component.master.stage;// MaiseyenkaDP gdfan 20.08.12 21:22

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.selectyour.gwtclient.rpc.dto.DataRow;
import com.selectyour.gwtclient.rpc.service.SelectPictureServiceAsync;

import java.util.List;

/**
 * select picture
 */
public class SelectPictureStage extends SelectStage {
    public SelectPictureStage(RootPanel rootPanel, SelectPictureServiceAsync selectDoorService,
                              String clientPhotoUrl, String windowFrameData) {
        super("Кликните по картине для увеличения", rootPanel, false, selectDoorService, clientPhotoUrl, windowFrameData);
    }

    @Override
    protected int getDataCellsInRow() {
        return 3;
    }

    @Override
    public void show(final Long[] selectIds) {
        super.show(selectIds);
        getSelectDoorService().getSelectPictureData(selectIds, getDataCellsInRow(),
                new AsyncCallback<List<DataRow>>() {
                    public void onSuccess(List<DataRow> dataRowList) {
                        fillData(dataRowList, getSelectHandler());
                    }

                    public void onFailure(Throwable caught) {
                        //Window.alert(caught.getMessage());//TODO:make error displaying for dev-mode
                    }
                }
        );
    }

}
