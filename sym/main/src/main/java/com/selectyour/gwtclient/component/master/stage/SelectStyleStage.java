package com.selectyour.gwtclient.component.master.stage;// MaiseyenkaDP gdfan 20.08.12 21:22

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.selectyour.gwtclient.rpc.dto.DataRow;
import com.selectyour.gwtclient.rpc.service.SelectPictureServiceAsync;

import java.util.List;

/**
 * select covering of picture
 */
public class SelectStyleStage extends SelectStage {
    public SelectStyleStage(RootPanel rootPanel, SelectPictureServiceAsync selectDoorService,
                            String clientPhotoUrl, String windowFrameData, boolean isDemo) {
        super(isDemo ? null : "Выберите стиль", rootPanel, true, selectDoorService, clientPhotoUrl, windowFrameData);
    }

    @Override
    public void show(final Long[] selectId) {
        super.show(selectId);
        getSelectDoorService().getSelectStyleData(getDataCellsInRow(),
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
