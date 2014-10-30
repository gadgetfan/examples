package com.selectyour.gwtclient.component.master.stage.selectglazingdecorstage;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.selectyour.gwtclient.rpc.dto.PictureDto;
import com.selectyour.gwtshared.PictureConstants;

public class PictureInfoPanel extends HorizontalPanel {

    private final Label styleLabel;
    private final Label priceLabel;
    private final Label nameLabel;
    private final Label sizeLabel;
    private final Image image;

    public PictureInfoPanel() {
        image = new Image();
        image.setStyleName("picture-info-picture");
        add(image);

        VerticalPanel infoPanel = new VerticalPanel();
        infoPanel.addStyleName("picture-info-data");
        nameLabel = new Label();
        nameLabel.addStyleName("name-label");
        styleLabel = new Label();
        priceLabel = new Label();
        priceLabel.addStyleName("price-label");

        sizeLabel = new Label();

        //add(new Label("Параметры выбранной картины:"));
        infoPanel.add(nameLabel);
        infoPanel.add(styleLabel);
        infoPanel.add(sizeLabel);
        infoPanel.add(priceLabel);
        add(infoPanel);
        //add(glazingLabel);
        //add(decorLabel);
    }

    public void showInfo(PictureDto pictureDto) {
        image.setUrl(pictureDto.getImageUrl());
        nameLabel.setText(pictureDto.getName());
        styleLabel.setText(PictureConstants.formatStyle(pictureDto.getStyle()));
        priceLabel.setText(PictureConstants.formatPrice(pictureDto.getPrice()));
        sizeLabel.setText(PictureConstants.formatSize(pictureDto.getWidth(), pictureDto.getHeight()));
    }
}
