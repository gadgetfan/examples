package com.selectyour.gwtclient.component.datacellwidget.processedphoto;// MaiseyenkaDP gdfan 19.08.12 14:25

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.selectyour.gwtclient.component.canvas.CCImage;
import com.selectyour.gwtclient.component.datacellwidget.DataCellWidget;
import com.selectyour.gwtclient.rpc.dto.DataCell;

/**
 * client's photo with inserted product's photo
 */
public class ProcessedPhotoWidget extends DataCellWidget {
    public static final String LOADING_BACKGROUND_STYLE = "loading-background";

    private final Canvas canvas;
    private final Label label;
    private CCImage clientPhoto;

    private String clientPhotoUrl;
    private String windowFrameData;

    public ProcessedPhotoWidget(DataCell dataCell, String clientPhotoUrl, String windowFrameData, int width, int height) {
        super(dataCell);
        this.clientPhotoUrl = clientPhotoUrl;
        this.windowFrameData = windowFrameData;

        this.setStyleName(LOADING_BACKGROUND_STYLE);

        this.canvas = Canvas.createIfSupported();
        initCanvas(width, height);

        this.label = new Label();
        initLabel(dataCell);

        processPhotoInBackground();
    }

    private void initLabel(DataCell dataCell) {
        label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        label.addStyleName("name-label");
        fillLabel(dataCell);
        this.add(label);
    }

    private void fillLabel(DataCell dataCell) {
        label.setText(dataCell.getName());
    }

    private void initCanvas(int width, int height) {
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        this.add(canvas);
    }

    /**
     * should be called after processPhotoInBackground loads client's photo
     *
     * @param dataCell
     */
    public void changeDataCell(DataCell dataCell) {
        ProcessPhotoHandler processPhotoHandler = new ProcessPhotoHandler(canvas, windowFrameData, dataCell.getPictureDto());
        processPhotoHandler.process(clientPhoto);
        fillLabel(dataCell);
    }

    /**
     * inserts product's photo on client's photo in back
     */
    private void processPhotoInBackground() {
        clientPhoto = new CCImage(canvas, clientPhotoUrl,
                new ProcessPhotoHandler(canvas, windowFrameData, getDataCell().getPictureDto()));
    }
}
