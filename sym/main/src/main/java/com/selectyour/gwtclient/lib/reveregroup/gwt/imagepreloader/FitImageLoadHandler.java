package com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader;

import com.google.gwt.event.shared.EventHandler;

public interface FitImageLoadHandler extends EventHandler {
    public void imageLoaded(FitImageLoadEvent event);
}