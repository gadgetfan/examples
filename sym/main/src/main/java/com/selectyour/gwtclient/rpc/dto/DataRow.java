package com.selectyour.gwtclient.rpc.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.List;

public class DataRow implements IsSerializable {
    private DataCell header;
    private List<DataCell> data;

    public DataRow() {
        this(null);
    }

    public DataRow(DataCell header) {
        this.header = header;
        this.data = new ArrayList<DataCell>();
    }

    public void addData(DataCell dataCell) {
        data.add(dataCell);
    }

    public DataCell getHeader() {
        return header;
    }

    public List<DataCell> getData() {
        return data;
    }

    public boolean hasHeader() {
        return (header != null);
    }
}
