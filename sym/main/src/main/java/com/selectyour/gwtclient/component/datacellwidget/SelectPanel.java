package com.selectyour.gwtclient.component.datacellwidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * panel with selectIds
 */
public class SelectPanel extends FlowPanel {
    /**
     * attribute to save selectIds
     */
    static private final String SELECT_ID_ATTRIBUTE = "syselectid";
    static private final String SELECT_IDS_DELIMITER = ";";

    private Long[] selectIds;

    protected SelectPanel(Long[] selectIds) {
        setSelectIds(selectIds);
    }

    public static SelectPanel fromWidget(Widget widget) {
        String[] selectIdStrings = widget.getElement().getAttribute(SELECT_ID_ATTRIBUTE).split(SELECT_IDS_DELIMITER);
        Long[] selectIds = null;
        if (selectIdStrings.length > 0) {
            selectIds = new Long[selectIdStrings.length];
            for (int i = 0; i < selectIdStrings.length; ++i) {
                Long selectId;
                try {
                    selectId = Long.valueOf(selectIdStrings[i]);
                } catch (NumberFormatException e) {
                    selectId = null;
                }
                selectIds[i] = selectId;
            }
        }
        return new SelectPanel(selectIds);
    }

    public Long[] getSelectIds() {
        return selectIds;
    }

    protected void setSelectIds(Long[] selectIds) {
        this.selectIds = selectIds;
        if (selectIds != null && selectIds.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (Long selectId : selectIds) {
                sb.append(selectId);
                sb.append(SELECT_IDS_DELIMITER);
            }
            getElement().setAttribute(SELECT_ID_ATTRIBUTE, sb.toString());
        }
    }
}
