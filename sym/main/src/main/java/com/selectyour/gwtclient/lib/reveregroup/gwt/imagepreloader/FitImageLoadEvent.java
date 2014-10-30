package com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader;

import com.google.gwt.event.shared.GwtEvent;

public class FitImageLoadEvent extends GwtEvent<FitImageLoadHandler> {
    private static final Type<FitImageLoadHandler> TYPE = new Type<FitImageLoadHandler>();
    protected boolean loadFailed;

    public FitImageLoadEvent(boolean loadFailed) {
        this.loadFailed = loadFailed;
    }

    public static Type<FitImageLoadHandler> getType() {
        return TYPE;
    }

    public boolean isLoadFailed() {
        return loadFailed;
    }

    public FitImage getFitImage() {
        return (FitImage) getSource();
    }

    @Override
    protected void dispatch(FitImageLoadHandler handler) {
        handler.imageLoaded(this);
    }

    @Override
    public Type<FitImageLoadHandler> getAssociatedType() {
        return TYPE;
    }

}