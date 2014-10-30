package com.selectyour.gwtclient.component.datacellwidget;

import com.google.gwt.user.client.ui.*;
import com.selectyour.gwtclient.rpc.dto.DataCell;

/**
 * diplays datacell with header
 */
public class HeaderWidget extends DataCellWidget {
    public HeaderWidget(final DataCell dataCell) {
        super(dataCell);

        Panel horizontalPanel = new HorizontalPanel();

        Panel leftPanel = new FlowPanel();
        if (dataCell.getPictureDto().hasImageUrl()) {
            leftPanel.add(new Image(dataCell.getImageUrl()));
        }
        if (dataCell.getPictureDto().hasName()) {
            Label headerLabel = new Label(dataCell.getName());
            leftPanel.add(headerLabel);
        }
        horizontalPanel.add(leftPanel);

        if (dataCell.getPictureDto().hasDescription()) {
            horizontalPanel.add(new Label(dataCell.getDescription()));
        }

        this.add(horizontalPanel);
    }
}
