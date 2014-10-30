package com.selectyour.gwtclient.component.datacellwidget;

import com.selectyour.gwtclient.rpc.dto.DataCell;

/**
 * widget, that displays datacell data
 */
public abstract class DataCellWidget extends SelectPanel {
    private final DataCell dataCell;


    protected DataCellWidget(DataCell dataCell) {
        super(dataCell.getSelectIds());
        this.dataCell = dataCell;
    }

    public DataCell getDataCell() {
        return dataCell;
    }
}
