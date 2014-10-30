package com.selectyour.gwtclient.component.master.stage.selectglazingdecorstage;// MaiseyenkaDP gdfan 20.08.12 21:22

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.selectyour.gwtclient.component.datacellwidget.processedphoto.ProcessedPhotoWidget;
import com.selectyour.gwtclient.component.master.stage.BaseStage;
import com.selectyour.gwtclient.rpc.dto.DataCell;
import com.selectyour.gwtclient.rpc.dto.PictureDto;
import com.selectyour.gwtclient.rpc.service.SelectPictureServiceAsync;

/**
 * Stage to show to the client big picture with image and order button
 */
public class BigPictureStage extends BaseStage {
    /**
     * attribute to store glazingId or decorId in radio button
     */
    static public final String RADIO_BUTTON_ID = "syid";
    /**
     * id of <input type="hidden"></input> with id of choosen picture
     */
    static private final String PICTURE_ID_DATA_INPUT_ID = "sy-id-input";
    private final boolean isDemo;
    private PictureInfoPanel pictureControlPanel;
    private Panel photoPanel;
    private TabPanel tabPanel;
    private InputElement pictureIdDataElement;
    private ProcessedPhotoWidget processedPhoto;

    public BigPictureStage(RootPanel rootPanel, SelectPictureServiceAsync selectDoorService,
                           String clientPhotoUrl, String windowFrameData, boolean isDemo) {
        super(rootPanel, null, selectDoorService, clientPhotoUrl, windowFrameData); //TODO: localize all strings

        this.isDemo = isDemo;
    }

    @Override
    public void show(final Long[] selectIds) {
        super.show(selectIds);

        Long pictureId = selectIds[0];
        fillData();
        showPicture(pictureId);

    }

    private void fillData() {
        tabPanel = new TabPanel();

        /*final StackPanel rightPanel = new StackPanel();
        rightPanel.addStyleName("picture-right-panel");*/

        /*FlowPanel buyPanel = new FlowPanel();
        buyPanel.add(new Label("Имя"));
        buyPanel.add(new Label("Телефон"));
        tabPanel.add(buyPanel, "Купить");*/

        //photo
        fillPhotoPanel();

        if (!isDemo) {
            //picture info
            Panel buyPanel = new FlowPanel();
            fillPictureInfoControls(buyPanel);
            tabPanel.add(buyPanel, "", false); //tab text is set in showPicture()
        }

        //backButton
        FlowPanel backPanel = new FlowPanel();
        if (!isDemo) {
            tabPanel.add(backPanel, "Посмотреть другие картины");
        } else {
            tabPanel.add(backPanel, "Другие картины");

        }

        tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
            public void onSelection(SelectionEvent<Integer> event) {
                Integer selectedTab = event.getSelectedItem();
                if (selectedTab == (isDemo ? 1 : 2)) {
                    History.back();
                } else {
                    setAdditionalElementVisibility(selectedTab == 1);
                }
                ;
                //Widget tabWidget = tabpanel.getWidget(tabId);
            }
        });

        tabPanel.selectTab(0);
        super.getMainPanel().add(tabPanel);

        processExternalElements();
    }

    @Override
    public void hide() {
        super.hide();
        processedPhoto = null;
    }

    private void fillPictureInfoControls(Panel panel) {
        pictureControlPanel = new PictureInfoPanel();
        panel.add(pictureControlPanel);
    }

    private void fillPhotoPanel() {
        photoPanel = new FlowPanel();
        tabPanel.add(photoPanel, ""); //in showPicture
    }

    public void showPicture(Long pictureId) {
        getSelectDoorService().getPictureInfo(pictureId,
                new AsyncCallback<PictureDto>() {
                    public void onSuccess(PictureDto pictureDto) {
                        if (!isDemo) {
                            tabPanel.getTabBar().setTabText(0, pictureDto.getStyle() + ": " + pictureDto.getName());
                            tabPanel.getTabBar().setTabText(1, "Купить " + pictureDto.getPrice());
                            pictureControlPanel.showInfo(pictureDto);
                        } else {
                            tabPanel.getTabBar().setTabText(0, "Купить " + pictureDto.getPrice());
                        }
                        showDoor(new DataCell(null, pictureDto));
                        saveDoorIdData(pictureDto.getId());
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());//TODO:make error displaying for dev-mode
                    }
                }
        );
    }

    private void showDoor(DataCell dataCell) {
        if (processedPhoto == null) {
            processedPhoto = new ProcessedPhotoWidget(dataCell, getClientPhotoUrl(), getWindowFrameData(),
                    Math.round(super.getMainPanel().getOffsetWidth()), Math.round(Window.getClientHeight() * 0.95f)); //TODO: use Layout/DockPanel instead
            photoPanel.add(processedPhoto);
        } else {
            processedPhoto.changeDataCell(dataCell);
        }

    }

    private void processExternalElements() {
        Element dide = Document.get().getElementById(PICTURE_ID_DATA_INPUT_ID);
        if (dide != null) {
            pictureIdDataElement = InputElement.as(dide);
        }
    }

    private void saveDoorIdData(Long pictureId) {
        pictureIdDataElement.setValue(pictureId.toString());
    }

}
