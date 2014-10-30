package com.selectyour.gwtclient.component.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Window;
import com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.selectyour.gwtclient.lib.reveregroup.gwt.imagepreloader.ImagePreloader;

/**
 * Display Image, defined by URL in canvas x, y position.
 */
public class CCImage extends AbstractStretchedComponent {
    private ImageElement imageElement;

    public CCImage(Canvas canvas, String url, final AfterFirstCCImageLoadHandler handler) {
        super(canvas, 0, 0, true);

        ImagePreloader.load(url, new ImageLoadHandler() {
            public void imageLoaded(ImageLoadEvent event) {
                if (event.isLoadFailed())
                    Window.alert("Image " + event.getImageUrl() + " failed to load.");
                else {
                    imageElement = ImageElement.as(event.takeImage().getElement());
                    prepareImageRectangle();
                    handler.process(getThis());
                }

            }
        });
    }

    private CCImage getThis() {
        return this;
    }

    @Override
    public int getHeight() {
        return imageElement.getHeight();
    }

    @Override
    public int getWidth() {
        return imageElement.getWidth();
    }

    @Override
    protected void internalDrawElement(Context2d context, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh) {
        context.drawImage(imageElement, sx, sy, sw, sh, dx, dy, dw, dh);
    }
}
