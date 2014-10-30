package com.selectyour.gwtclient.component.imagetable;// MaiseyenkaDP gdfan 19.08.12 10:36

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.selectyour.gwtclient.component.datacellwidget.SelectPanel;
import com.selectyour.gwtclient.component.datasource.WidgetSource;
import com.selectyour.gwtclient.component.master.stage.SelectHandler;

import java.util.List;

/**
 * Table with images in cells. Loads data in other pseudo thread (by timer)
 */
public class WidgetGrid extends FlexTable {
    /**
     * style for displaying cursor on a row or on a cell
     */
    static private final String ROW_CURSOR_STYLE = "row-cursor";
    static private final String CELL_CURSOR_STYLE = "cell-cursor";

    /**
     * width in percent of header (first) column of row
     */
    static private final int HEADER_WIDTH = 20;
    /**
     * time in milliseconds between loading widgets for cells
     */
    static private final int BETWEEN_LOADING_DELAY = 100;

    /**
     * data source for table
     */
    private final List<WidgetSource> widgetSourceList;
    private final SelectHandler selectHandler;

    public WidgetGrid(List<WidgetSource> widgetSourceList, SelectHandler selectHandler, boolean rowCursor) {
        super();
        this.widgetSourceList = widgetSourceList;
        this.selectHandler = selectHandler;

        addStyleName("centered-table");
        setCursor(rowCursor);
        setEvents();
        loadData();
    }

    private void setCursor(boolean rowCursor) {
        if (rowCursor) {
            removeStyleName(CELL_CURSOR_STYLE);
            addStyleName(ROW_CURSOR_STYLE);
        } else {
            removeStyleName(ROW_CURSOR_STYLE);
            addStyleName(CELL_CURSOR_STYLE);
        }
    }

    private void setEvents() {
        addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Cell cell = getCellForEvent(event);
                if (cell != null) {
                    Long[] selectId = SelectPanel.fromWidget(getWidget(cell.getRowIndex(), cell.getCellIndex())).getSelectIds();
                    selectHandler.onSelect(selectId);
                }
            }
        });
    }

    private void loadData() {
        //initDataLoading();

        int r = 0;
        for (WidgetSource widgetSource : widgetSourceList) {
            widgetSource.first();
            int c = 0;
            while (true) {
                Widget widget = widgetSource.getNext();
                if (widget != null) {
                    setWidget(r, c, widget);
                } else {
                    break;
                }

                ++c;
            }

            ++r;
        }
    }
}
