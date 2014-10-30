package com.selectyour.gwtclient.component.master.stage;// MaiseyenkaDP gdfan 20.08.12 21:22

import com.google.gwt.user.client.ui.RootPanel;
import com.selectyour.gwtclient.component.datasource.SelectPictureWidgetSource;
import com.selectyour.gwtclient.component.datasource.WidgetSource;
import com.selectyour.gwtclient.component.imagetable.WidgetGrid;
import com.selectyour.gwtclient.rpc.dto.DataRow;
import com.selectyour.gwtclient.rpc.service.SelectPictureServiceAsync;

import java.util.ArrayList;
import java.util.List;

/**
 * Stage where client selects one picture's row (default rowCursor=true)
 * or one picture from cell - (rowCursor=false)
 */
public abstract class SelectStage extends BaseStage {
    private boolean rowCursor;

    public SelectStage(String title, RootPanel rootPanel, boolean rowCursor, SelectPictureServiceAsync selectDoorService,
                       String clientPhotoUrl, String windowFrameData) {
        super(rootPanel, title, selectDoorService, clientPhotoUrl, windowFrameData); //TODO: localize all strings
        this.rowCursor = rowCursor;
    }

    protected void fillData(List<DataRow> dataRowList, final SelectHandler selectHandler) {
        List<WidgetSource> widgetSourceList = new ArrayList<WidgetSource>();

        //use only first row for calculations. Put in cycle, if all rows should calculate separately
        DataRow firstRow = dataRowList.get(0);
        int cellWidth = Math.round((getMainPanel().getElement().getClientWidth() - firstRow.getData().size() * (2/*px*/ + 1)) //according symodule.css table.cell-cursor {border-spacing: 2px;}
                / (firstRow.getData().size() + (firstRow.getHeader() == null ? 0 : 0.5f)));
        int cellHeight = Math.round(cellWidth * 0.75f);
        for (DataRow dataRow : dataRowList) {
            widgetSourceList.add(new SelectPictureWidgetSource(getClientPhotoUrl(), getWindowFrameData(), dataRow,
                    cellWidth, cellHeight));
        }

        WidgetGrid widgetGrid = new WidgetGrid(widgetSourceList, new SelectHandler() {
            public void onSelect(Long[] selectId) {
                unBuildStage();
                selectHandler.onSelect(selectId);
            }
        }, rowCursor);
        getMainPanel().add(widgetGrid);
    }
}
