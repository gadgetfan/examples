package com.selectyour.gwtclient.component.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public abstract class AbstractCanvasComponent implements ICanvasComponent {
    private final Context2d context;

    /**
     * top, left coordinates
     */
    protected int x;
    protected int y;

    /**
     * @param canvas - canvas to draw component
     */
    public AbstractCanvasComponent(Canvas canvas, int x, int y) {
        this.context = canvas.getContext2d();
        this.x = x;
        this.y = y;
    }

    /**
     * draws the component
     */
    public void draw() {
        prepareContext(context);
        internalDraw(context);
    }

    protected void prepareContext(Context2d context) {
    }

    protected abstract void internalDraw(Context2d context);

    protected Context2d getContext() {
        return context;
    }
}
