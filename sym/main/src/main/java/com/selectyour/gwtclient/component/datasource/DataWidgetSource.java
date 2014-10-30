package com.selectyour.gwtclient.component.datasource;// MaiseyenkaDP gdfan 19.08.12 12:18

import com.google.gwt.user.client.ui.Widget;
import com.selectyour.gwtclient.rpc.dto.DataCell;
import com.selectyour.gwtclient.rpc.dto.DataRow;

import java.util.Iterator;

/**
 * data source, that gets all his data from DataRow
 */
public abstract class DataWidgetSource implements WidgetSource {
    private DataRow dataRow;

    private Iterator<DataCell> dataCellIterator;
    private boolean needProcessHeader;

    protected DataWidgetSource(DataRow dataRow) {
        this.dataRow = dataRow;
        //first();
    }

    public void first() {
        dataCellIterator = dataRow.getData().iterator();
        needProcessHeader = dataRow.hasHeader();
    }

    public Widget getNext() {
        if (dataCellIterator.hasNext()) {
            return getWidgetForDataCell(dataCellIterator.next());
        } else {
            if (needProcessHeader) {
                needProcessHeader = false;
                return getWidgetForHeaderCell(dataRow.getHeader());
            } else {
                return null;
            }
        }
    }

    protected abstract Widget getWidgetForHeaderCell(DataCell dataCell);

    protected abstract Widget getWidgetForDataCell(DataCell dataCell);
}
