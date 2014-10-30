package com.selectyour.gwtclient.rpc.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Data transfer object for Picture entity
 */
public class PictureDto implements IsSerializable {
    private Long id;
    private String style;
    private String price;
    private String imageUrl;
    private String name;
    private String description;
    private float kx;
    private float ky;
    private double width;
    private double height;

    public PictureDto() {
    }

    public PictureDto(String description) {
        this.description = description;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getKx() {
        return kx;
    }

    public void setKx(float kx) {
        this.kx = kx;
    }

    public float getKy() {
        return ky;
    }

    public void setKy(float ky) {
        this.ky = ky;
    }

    public boolean hasImageUrl() {
        return (imageUrl != null && !imageUrl.isEmpty());
    }

    public boolean hasName() {
        return (name != null && !name.isEmpty());
    }

    public boolean hasDescription() {
        return (description != null && !description.isEmpty());
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
