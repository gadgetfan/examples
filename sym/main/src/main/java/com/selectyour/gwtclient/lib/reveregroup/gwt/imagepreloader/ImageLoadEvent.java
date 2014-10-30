package com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Image;

public class ImageLoadEvent extends GwtEvent<ImageLoadHandler> {
    private static final Type<ImageLoadHandler> TYPE = new Type<ImageLoadHandler>();
    protected ImageElement image;
    protected String url;
    protected Dimensions dimensions;
    protected boolean imageTaken;

    public ImageLoadEvent(ImageElement image, Dimensions dimensions) {
        this.image = image;
        this.dimensions = dimensions;
    }

    public ImageLoadEvent(String url, Dimensions dimensions) {
        this.url = url;
        this.dimensions = dimensions;
    }

    public static Type<ImageLoadHandler> getType() {
        return TYPE;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public Image takeImage() {
        imageTaken = true;
        if (image == null) {
            return new Image(url);
        } else {
            Image ret = new ImageFromElement(image);
            //image = null; //MDP fix: cannot load one image many times
            //TODO: how this library works ?
            return ret;
        }
    }

    public String getImageUrl() {
        if (url != null)
            return url;
        return image.getSrc();
    }

    public boolean isImageTaken() {
        return imageTaken;
    }

    public boolean isLoadFailed() {
        return dimensions == null;
    }

    @Override
    protected void dispatch(ImageLoadHandler handler) {
        handler.imageLoaded(this);
    }

    @Override
    public Type<ImageLoadHandler> getAssociatedType() {
        return TYPE;
    }

    private static class ImageFromElement extends Image {
        public ImageFromElement(ImageElement element) {
            super(element);
        }
    }

}