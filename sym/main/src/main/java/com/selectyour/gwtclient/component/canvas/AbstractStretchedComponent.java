package com.selectyour.gwtclient.component.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.selectyour.gwtclient.transformation.base.Rectangle;

/**
 * Ancestor for elements, that can be stretched
 */
public abstract class AbstractStretchedComponent extends AbstractCanvasComponent {
    /**
     * stretch the image on all canvas with proportions
     */
    private boolean stretch;
    /**
     * place, that occupies image on Canvas
     */
    private Rectangle imageRectangle;


    public AbstractStretchedComponent(Canvas canvas, int x, int y, boolean stretch) {
        super(canvas, x, y);
        this.stretch = stretch;
    }

    //implement in child
    public abstract int getHeight();

    public abstract int getWidth();

    protected abstract void internalDrawElement(Context2d context, int sx, int sy, int sw, int sh, int dx, int dy,
                                                int dw, int dh);

    protected void prepareImageRectangle() {
        if (stretch) {
            double kwhDst = (double) getContext().getCanvas().getWidth() / getContext().getCanvas().getHeight();
            double kwhSrc = (double) getWidth() / getHeight();
            int dw, dh, dx, dy;
            if (kwhSrc > kwhDst) {
                dw = getContext().getCanvas().getWidth();
                dh = (int) Math.round(dw / kwhSrc);
                dx = 0;
                dy = 0;//display on top border (getContext().getCanvas().getHeight() - dh) / 2;
            } else {
                dh = getContext().getCanvas().getHeight();
                dw = (int) Math.round(dh * kwhSrc);
                dx = (getContext().getCanvas().getWidth() - dw) / 2;
                dy = 0;
            }

            imageRectangle = new Rectangle(dx, dy, dw, dh);
        } else {
            imageRectangle = new Rectangle(x, y, getWidth(), getHeight());
        }
    }

    @Override
    protected void internalDraw(Context2d context) {
        if (imageRectangle == null) {
            prepareImageRectangle();
        }

        if (stretch) {
            double kwhDst = (double) context.getCanvas().getWidth() / context.getCanvas().getHeight();
            double kwhSrc = (double) getWidth() / getHeight();
            int dw, dh, dx, dy;
            if (kwhSrc > kwhDst) {
                dw = context.getCanvas().getWidth();
                dh = (int) Math.round(dw / kwhSrc);
                dx = 0;
                dy = 0;//display on top border (context.getCanvas().getHeight() - dh) / 2;
            } else {
                dh = context.getCanvas().getHeight();
                dw = (int) Math.round(dh * kwhSrc);
                dx = (context.getCanvas().getWidth() - dw) / 2;
                dy = 0;
            }

            internalDrawElement(context, 0, 0, getWidth(), getHeight(), imageRectangle.x, imageRectangle.y, imageRectangle.width, imageRectangle.height);
        } else {
            internalDrawElement(context, 0, 0, getWidth(), getHeight(), imageRectangle.x, imageRectangle.y, imageRectangle.width, imageRectangle.height);
        }
    }

    @Override
    protected void prepareContext(Context2d context) {
        context.setGlobalAlpha(1);
        context.setGlobalCompositeOperation(Context2d.Composite.SOURCE_OVER);
    }

    public Rectangle getImageRectangle() {
        return imageRectangle;
    }
}
