package com.selectyour.gwtclient;// MaiseyenkaDP gdfan 19.08.12 10:28

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.selectyour.gwtclient.component.master.StageMaster;
import com.selectyour.gwtclient.component.master.stage.SelectPictureStage;
import com.selectyour.gwtclient.component.master.stage.SelectStyleStage;
import com.selectyour.gwtclient.component.master.stage.selectglazingdecorstage.BigPictureStage;
import com.selectyour.gwtclient.rpc.service.SelectPictureService;
import com.selectyour.gwtclient.rpc.service.SelectPictureServiceAsync;
import com.selectyour.gwtshared.SharedConstants;

public class SelectYourModuleNew {
    /**
     * Id of div with root panel
     */
    private static final String ROOT_PANEL_ID = "sy-main";
    /**
     * Points to source of client's photo. Attribute of root panel.
     */
    static private final String CLIENT_PHOTO_SOURCE_ATTRIBUTE = "syclientphoto";
    /**
     * attribute for window frame data
     */
    static private final String WINDOW_FRAME_DATA_ATTRIBUTE = "sywframedata";
    /**
     * show only
     */
    static private final String IS_DEMO_ATTRIBUTE = "isDemo";

    private RootPanel rootPanel;

    private SelectPictureServiceAsync selectPictureService;

    public void onModuleLoad() {
        this.rootPanel = RootPanel.get(ROOT_PANEL_ID);

        createServices();

        String clientPhotoUrl = rootPanel.getElement().getAttribute(CLIENT_PHOTO_SOURCE_ATTRIBUTE);
        String windowFrameData = rootPanel.getElement().getAttribute(WINDOW_FRAME_DATA_ATTRIBUTE);
        boolean isDemo = Boolean.parseBoolean(rootPanel.getElement().getAttribute(IS_DEMO_ATTRIBUTE));

        StageMaster stageMaster = new StageMaster();

        //select style
        stageMaster.addStage(new SelectStyleStage(rootPanel, selectPictureService, clientPhotoUrl, windowFrameData, isDemo));
        //select picture
        stageMaster.addStage(new SelectPictureStage(rootPanel, selectPictureService, clientPhotoUrl, windowFrameData));
        //see big picture
        stageMaster.addStage(new BigPictureStage(rootPanel, selectPictureService, clientPhotoUrl, windowFrameData, false));

        stageMaster.start(null);
        //selectGlazingDecorStage.show(new Long[]{16L});
    }

    private void createServices() {
        selectPictureService = GWT.create(SelectPictureService.class);
        ((ServiceDefTarget) selectPictureService).setServiceEntryPoint("/" + SharedConstants.WEB_CONTEXT + "/syService/selectDoor");
    }

}
