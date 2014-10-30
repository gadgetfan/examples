package com.selectyour.gwtclient.component.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.selectyour.gwtclient.transformation.IntPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Component to draw window frame, where blinds are showed
 */
public class WindowFrame extends AbstractCanvasComponent {
    static public final int TOP_LEFT = 0;
    static public final int TOP_RIGHT = 1;
    static public final int BOTTOM_RIGHT = 2;
    static public final int BOTTOM_LEFT = 3;
    /**
     * radius of point of frame
     */
    static public final int POINT_RADIUS = 3;
    /**
     * max points in frame
     */
    static final int POINTS_CNT = 4;
    /**
     * list to save points
     */
    private final List<IntPoint> pointList;

    public WindowFrame(Canvas canvas) {
        super(canvas, 0, 0);

        pointList = new ArrayList<IntPoint>(POINTS_CNT);
    }

    /**
     * adds new point to frame. If in frame there are 4 points in frame does nothing
     *
     * @param x
     * @param y
     */
    public void addPoint(int x, int y) {
        if (pointList.size() < POINTS_CNT) {
            pointList.add(new IntPoint(x, y));
        }
    }

    /**
     * removes last added point (if it existed)
     */
    public void removeLastPoint() {
        if (pointList.size() > 0) {
            pointList.remove(pointList.size() - 1);
        }
    }

    /**
     * has all needed point
     *
     * @return
     */
    public boolean isFull() {
        return (POINTS_CNT == pointList.size());
    }

    /**
     * has no points
     *
     * @return
     */
    public boolean isEmpty() {
        return (POINTS_CNT == 0);
    }

    /**
     * Returns points TOP_LEFT .. BOTTOM_LEFT
     *
     * @param direction see const TOP_LEFT .. BOTTOM_LEFT
     * @return
     */
    public IntPoint getPoint(final int direction) {
        int maxK = Integer.MIN_VALUE;
        int dx, dy;
        IntPoint bestPoint = null;
        for (IntPoint point : pointList) {
            switch (direction) {
                case TOP_LEFT:
                    dx = -1;
                    dy = -1;
                    break;
                case TOP_RIGHT:
                    dx = 1;
                    dy = -1;
                    break;
                case BOTTOM_LEFT:
                    dx = -1;
                    dy = 1;
                    break;
                case BOTTOM_RIGHT:
                    dx = 1;
                    dy = 1;
                    break;
                default:
                    return null;
            }
            int k = dx * point.getX() + dy * point.getY();
            if (k > maxK) {
                maxK = k;
                bestPoint = point;
            }
        }

        return bestPoint;
    }

    /**
     * Returns points TOP_LEFT .. BOTTOM_LEFT
     *
     * @param direction    see const TOP_LEFT .. BOTTOM_LEFT
     * @param distalLength adds distalLength pixels from center to point
     * @return
     */
    public IntPoint getPointWithDistalLength(final int direction, int distalLength) {
        int maxK = Integer.MIN_VALUE;
        int k = Integer.MIN_VALUE;
        int dx, dy, bestDx = 0, bestDy = 0;
        IntPoint bestPoint = null;
        for (IntPoint point : pointList) {
            switch (direction) {
                case TOP_LEFT:
                    dx = -1;
                    dy = -1;
                    break;
                case TOP_RIGHT:
                    dx = 1;
                    dy = -1;
                    break;
                case BOTTOM_LEFT:
                    dx = -1;
                    dy = 1;
                    break;
                case BOTTOM_RIGHT:
                    dx = 1;
                    dy = 1;
                    break;
                default:
                    return null;
            }
            k = dx * point.getX() + dy * point.getY();
            if (k > maxK) {
                maxK = k;
                bestPoint = point;
                bestDx = dx;
                bestDy = dy;
            }
        }
        return new IntPoint(bestPoint.getX() + bestDx * distalLength, bestPoint.getY() + bestDy * distalLength);
    }

    /**
     * returns min x for point
     *
     * @return
     */
    public int getMinX() {
        if (isFull()) {
            int result = Integer.MAX_VALUE;
            for (IntPoint point : pointList) {
                result = Math.min(result, point.getX());
            }
            return result;
        } else {
            return 0;
        }
    }

    /**
     * returns min y for point
     *
     * @return
     */
    public int getMinY() {
        if (isFull()) {
            int result = Integer.MAX_VALUE;
            for (IntPoint point : pointList) {
                result = Math.min(result, point.getY());
            }
            return result;
        } else {
            return 0;
        }
    }

    public float getLength(int direction1, int direction2) {
        IntPoint point1 = getPoint(direction1);
        IntPoint point2 = getPoint(direction2);

        return (float) Math.sqrt((point2.getX() - point1.getX()) * (point2.getX() - point1.getX())
                + (point2.getY() - point1.getY()) * (point2.getY() - point1.getY()));
    }

    @Override
    protected void internalDraw(Context2d context) {
        if (pointList.size() > 0) {
            //lines
            context.beginPath();

            context.moveTo(getPoint(WindowFrame.TOP_LEFT).getX(),
                    getPoint(WindowFrame.TOP_LEFT).getY());
            context.lineTo(getPoint(WindowFrame.TOP_RIGHT).getX(),
                    getPoint(WindowFrame.TOP_RIGHT).getY());
            context.lineTo(getPoint(WindowFrame.BOTTOM_RIGHT).getX(),
                    getPoint(WindowFrame.BOTTOM_RIGHT).getY());
            context.lineTo(getPoint(WindowFrame.BOTTOM_LEFT).getX(),
                    getPoint(WindowFrame.BOTTOM_LEFT).getY());

            context.closePath();
            context.fill();

            //points
            prepareContextFill(context);
            for (int i = 0; i < pointList.size(); ++i) {
                context.beginPath();
                IntPoint point = pointList.get(i);
                context.arc(point.getX(), point.getY(), POINT_RADIUS, 0, 2 * Math.PI);
                context.closePath();
                context.fill();
            }

            prepareContextStroke(context);
            for (int i = 0; i < pointList.size(); ++i) {
                context.beginPath();
                IntPoint point = pointList.get(i);
                context.arc(point.getX(), point.getY(), POINT_RADIUS, 0, 2 * Math.PI);
                context.closePath();
                context.stroke();
            }
        }
    }

    @Override
    protected void prepareContext(Context2d context) {
        context.setFillStyle("#FFFFFF");
        context.setGlobalAlpha(0.75);
        context.setGlobalCompositeOperation(Context2d.Composite.SOURCE_OVER);
        //see additional changing in prepareContextFill, prepareContextStroke
    }

    private void prepareContextFill(Context2d context) {
        context.setGlobalAlpha(1);
        context.setFillStyle("#ffffff");
    }

    private void prepareContextStroke(Context2d context) {
        context.setGlobalAlpha(1);
        context.setLineWidth((POINT_RADIUS + 1) / 2);
        context.setStrokeStyle("#000000");
    }

    //Use only for serialization in WindowFrameList.getDataString()!
    List<IntPoint> getPointList() {
        return pointList;
    }
}
