package com.selectyour.gwtclient.component.master.stage;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.selectyour.gwtclient.rpc.service.SelectPictureServiceAsync;

/**
 * ancestor of all stages with client photo
 */
public abstract class BaseStage implements Stage {
    /**
     * element to show or hide on stage
     */
    private static final String ADDITIONAL_ELEMENT_ID = "sy-additional";
    private final RootPanel rootPanel;
    private final SelectPictureServiceAsync selectDoorService;
    private final String clientPhotoUrl;
    private final String windowFrameData;
    private Label nameLabel;
    private Panel mainPanel;
    private SelectHandler selectHandler;

    protected BaseStage(RootPanel rootPanel, String title, SelectPictureServiceAsync selectDoorService,
                        String clientPhotoUrl, String windowFrameData) {
        this.rootPanel = rootPanel;

        this.mainPanel = new FlowPanel();
        //mainPanel.setStyleName("stage-data-panel");

        if (title != null) {
            this.nameLabel = new Label(title);
            nameLabel.addStyleName("stage-title");
        }

        this.selectDoorService = selectDoorService;
        //TODO: get this data from service
        this.clientPhotoUrl = clientPhotoUrl;
        this.windowFrameData = windowFrameData;
    }

    private void buildStage() {
        setAdditionalElementVisibility(false);
        if (nameLabel != null) {
            rootPanel.add(nameLabel);
        }
        rootPanel.add(mainPanel);
    }

    public void unBuildStage() {
        if (nameLabel != null) {
            rootPanel.remove(nameLabel);
        }
        rootPanel.remove(mainPanel);
        mainPanel.clear();
    }

    public void hide() {
        unBuildStage();
    }

    protected int getDataCellsInRow() {
        return 2;
    }

    protected Panel getMainPanel() {
        return mainPanel;
    }

    public void show(final Long[] selectIds) {
        buildStage();
    }

    public SelectPictureServiceAsync getSelectDoorService() {
        return selectDoorService;
    }

    public String getClientPhotoUrl() {
        return clientPhotoUrl;
    }

    public String getWindowFrameData() {
        return windowFrameData;
    }

    public SelectHandler getSelectHandler() {
        return selectHandler;
    }

    public void setSelectHandler(SelectHandler selectHandler) {
        this.selectHandler = selectHandler;
    }

    protected void setAdditionalElementVisibility(boolean isVisible) {
        Element element = Document.get().getElementById(ADDITIONAL_ELEMENT_ID);
        if (element != null) {
            if (isVisible) {
                element.setAttribute("style", "display:block;");
            } else {
                element.setAttribute("style", "display:none");
            }
        }
    }
}
