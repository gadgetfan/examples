package com.selectyour.gwtclient.component.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.selectyour.gwtclient.transformation.IntPoint;
import com.selectyour.gwtclient.transformation.base.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * List of WindowFrame
 */
public class WindowFrameList implements ICanvasComponent {

    static private final String STRING_DATA_DELIMITER = ";";

    private List<WindowFrame> frameList;
    private Canvas canvas;

    public WindowFrameList(Canvas canvas) {
        this.frameList = new ArrayList<WindowFrame>();
        this.canvas = canvas;
        addFrame();
    }

    public String getDataString(Rectangle photoRectangle) {
        StringBuilder data = new StringBuilder();

        for (WindowFrame frame : frameList) {
            serializePoint(photoRectangle, data, frame.getPoint(WindowFrame.TOP_RIGHT));
            serializePoint(photoRectangle, data, frame.getPoint(WindowFrame.BOTTOM_RIGHT));
            serializePoint(photoRectangle, data, frame.getPoint(WindowFrame.BOTTOM_LEFT));
            serializePoint(photoRectangle, data, frame.getPoint(WindowFrame.TOP_LEFT));
        }

        return data.toString();
    }

    private void serializePoint(Rectangle photoRectangle, StringBuilder data, IntPoint point) {
        data.append((float) ((point.getX() - photoRectangle.x) / photoRectangle.getWidth()));
        data.append(STRING_DATA_DELIMITER);
        data.append((float) ((point.getY() - photoRectangle.y) / photoRectangle.getHeight()));
        data.append(STRING_DATA_DELIMITER);
    }

    public void setDataString(String dataString, Rectangle photoRectangle, float kx, float ky) {
        frameList.clear();
        if (dataString != null && !dataString.isEmpty()) {
            float[] coordinates = ScalePointsUtils.scaleCoordinates(toFloatArray(dataString), kx, ky);
            fillCoordinates(coordinates, photoRectangle);
        } else {
            addFrame();
        }
    }

    private void fillCoordinates(float[] coordinates, Rectangle photoRectangle) {
        frameList.clear();
        int p = 0;
        while (p < coordinates.length) {
            if ((p % (2 * WindowFrame.POINTS_CNT)) == 0) {
                addFrame();
            }
            int x = (int) Math.round(photoRectangle.x + photoRectangle.getWidth() * coordinates[p++]);
            int y = (int) Math.round(photoRectangle.y + photoRectangle.getHeight() * coordinates[p++]);
            addPoint(x, y);
        }
    }

    /*public void setScale(float kx, float ky) {
        float[] scaledCoordinates = ScalePointsUtils.scaleCoordinates(coordinates, kx, ky);
        fillCoordinates(scaledCoordinates, photoRectangle);
    }*/

    private float[] toFloatArray(String dataString) {
        String[] strings = dataString.split(STRING_DATA_DELIMITER);
        float[] results = new float[strings.length];
        for (int i = 0; i < results.length; ++i) {
            results[i] = Float.valueOf(strings[i]);
        }

        return results;
    }

    /**
     * adds new point to frame list. If in current frame are, then creates new frame
     *
     * @param x
     * @param y
     */
    public void addPoint(int x, int y) {
        if (getCurrent().isFull()) {
            addFrame();
        }
        getCurrent().addPoint(x, y);
    }

    /**
     * removes last added point (if it existed)
     */
    public void removeLastPoint() {
        if (!getCurrent().isEmpty()) {
            getCurrent().removeLastPoint();
        } else {
            removeFrame();
            getCurrent().removeLastPoint();
        }
    }

    /**
     * true, if frame list contains only full frames
     *
     * @return
     */
    public boolean isCorrect() {
        return getCurrent().isFull();
    }

    /**
     * true, if first frame in list exists and is full (correct)
     *
     * @return
     */
    public boolean isFirstFrameFull() {
        return (frameList.size() > 0 && frameList.get(0).isFull());
    }

    public void draw() {
        for (WindowFrame frame : frameList) {
            frame.draw();
        }
    }

    public List<WindowFrame> getFrameList() {
        return frameList;
    }

    /**
     * returns current (last) frame, or creates first, if no frames exists
     *
     * @return
     */
    private WindowFrame getCurrent() {
        return frameList.get(frameList.size() - 1);
    }

    /**
     * adds new frame
     */
    private void addFrame() {
        WindowFrame frame = new WindowFrame(canvas);
        frameList.add(frame);
    }

    /**
     * removes frame, if it's not last
     */
    public void removeFrame() {
        if (frameList.size() > 1) {
            frameList.remove(frameList.size() - 1);
            /*float[] newCoordinates = new float[coordinates.length - 8];
            for (int i = 0; i < newCoordinates.length; ++i) {
                newCoordinates[i] = coordinates[i];
            }
            this.coordinates = newCoordinates;*/
        }
    }

}
