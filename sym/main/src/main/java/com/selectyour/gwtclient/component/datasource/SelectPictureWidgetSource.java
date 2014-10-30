package com.selectyour.gwtclient.component.datasource;// MaiseyenkaDP gdfan 19.08.12 13:40

import com.google.gwt.user.client.ui.Widget;
import com.selectyour.gwtclient.component.datacellwidget.HeaderWidget;
import com.selectyour.gwtclient.component.datacellwidget.processedphoto.ProcessedPhotoWidget;
import com.selectyour.gwtclient.rpc.dto.DataCell;
import com.selectyour.gwtclient.rpc.dto.DataRow;

/**
 * returns canvas with client's photo with inserted product
 */
public class SelectPictureWidgetSource extends DataWidgetSource {
    /**
     * interval in milliseconds between checking, that client photo is loaded
     */
    static private final int BETWEEN_TRY_INTERVAL = 100;

    //private final Map<String, Canvas> canvasMap;
    private final String clientPhotoUrl;
    private final String windowFrameData;
    private final int cellWidth;
    private final int cellHeight;

    public SelectPictureWidgetSource(String clientPhotoUrl, String windowFrameData, DataRow dataRow, int cellWidth, int cellHeight) {
        super(dataRow);

        this.clientPhotoUrl = clientPhotoUrl;
        this.windowFrameData = windowFrameData;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    @Override
    protected Widget getWidgetForDataCell(DataCell dataCell) {
        ProcessedPhotoWidget processedPhotoWidget = new ProcessedPhotoWidget(dataCell, clientPhotoUrl, windowFrameData, cellWidth, cellHeight);

        return processedPhotoWidget;
    }

    @Override
    protected Widget getWidgetForHeaderCell(DataCell dataCell) {
        HeaderWidget headerWidget = new HeaderWidget(dataCell);

        return headerWidget;
    }
}
