package com.selectyour.gwtclient.component.canvas;

import com.selectyour.gwtclient.transformation.IntPoint;
import com.selectyour.gwtclient.transformation.PerspectiveTransform;

import java.util.List;

/**
 * Created by qwerty on 17.06.2014.
 * Scales quad proportionally
 */
public class ScalePointsUtils {
    public static float[] scaleCoordinates(float[] coordinates, float kx, float ky) {

        if (coordinates.length != 8) {
            throw new IllegalArgumentException("ScalePointsUtils supports only quad");
        }
        float[] results = new float[coordinates.length];
        for (int i = 0; i < results.length; ++i) {
            results[i] = coordinates[i];
        }

        float[] standardPoints = new float[]{
                1f, -1f,
                1f, 1f,
                -1f, 1f,
                -1f, -1f
        };

        /*float[] center = calcIntersection(coordinates);

        for (int i = 0; i < results.length / 2; ++i) {
            results[2 * i] = results[2 * i] - center[0];
            results[2 * i + 1] = results[2 * i + 1] - center[1];
        }*/

        PerspectiveTransform transform = PerspectiveTransform.quadrilateralToQuadrilateral(
                standardPoints[0], standardPoints[1],
                standardPoints[2], standardPoints[3],
                standardPoints[4], standardPoints[5],
                standardPoints[6], standardPoints[7],
                results[0], results[1],
                results[2], results[3],
                results[4], results[5],
                results[6], results[7]
        );

        for (int i = 0; i < standardPoints.length / 2; ++i) {
            standardPoints[2 * i] = standardPoints[2 * i] * kx;
            standardPoints[2 * i + 1] = standardPoints[2 * i + 1] * ky;
        }

        transform.transformPoints(standardPoints);

        /*for (int i = 0; i < standardPoints.length / 2; ++i) {
            standardPoints[2 * i] = standardPoints[2 * i] + center[0];
            standardPoints[2 * i + 1] = standardPoints[2 * i + 1] + center[1];
        }*/

        return standardPoints;
    }

    /*public static void scalePoints(List<IntPoint> pointList, float kx, float ky) {
        if (pointList.size() != 4) {
            throw new IllegalArgumentException("ScalePointsUtils supports only quad");
        }

        List<IntPoint> standardPointList = new ArrayList<IntPoint>(Arrays.asList(new IntPoint[]{
                new IntPoint(1, -1),
                new IntPoint(1, 1),
                new IntPoint(-1, 1),
                new IntPoint(-1, -1),

        }));
        IntPoint center = calcIntersection(pointList);

        for (IntPoint point : pointList) {
            point.setXY(
                    Math.round((point.getX() - center.getX())),
                    Math.round((point.getY() - center.getY()))
            );
        }

        PerspectiveTransform transform = PerspectiveTransform.quadrilateralToQuadrilateral(
                standardPointList.get(0).getX(), standardPointList.get(0).getY(),
                standardPointList.get(1).getX(), standardPointList.get(1).getY(),
                standardPointList.get(2).getX(), standardPointList.get(2).getY(),
                standardPointList.get(3).getX(), standardPointList.get(3).getY(),
                pointList.get(0).getX(), pointList.get(0).getY(),
                pointList.get(1).getX(), pointList.get(1).getY(),
                pointList.get(2).getX(), pointList.get(2).getY(),
                pointList.get(3).getX(), pointList.get(3).getY()
        );

        for (IntPoint point : standardPointList) {
            point.scale(kx, ky);
        }

        float[] points = toPoints(standardPointList);
        transform.transformPoints(points);
        saveToList(points, pointList);

        for (IntPoint point : pointList) {
            point.setXY(
                    Math.round((point.getX() + center.getX())),
                    Math.round((point.getY() + center.getY()))
            );
        }

    }

    private static float[] toPoints(List<IntPoint> pointList) {
        float[] results = new float[pointList.size() * 2];
        for (int i = 0; i < pointList.size(); ++i) {
            IntPoint point = pointList.get(i);
            results[2 * i] = point.getX();
            results[2 * i + 1] = point.getY();
        }

        return results;
    }


    private static void saveToList(float[] points, List<IntPoint> pointList) {
        for (int i = 0; i < pointList.size(); ++i) {
            pointList.get(i).setXY(
                    Math.round(points[2 * i]),
                    Math.round(points[2 * i + 1])
            );
        }
    }*/

    /**
     * Computes the intersection between two lines. pointList should have points in correct order
     */
    private static IntPoint calcIntersection(List<IntPoint> pointList) {
        int x1 = pointList.get(0).getX();
        int y1 = pointList.get(0).getY();
        int x2 = pointList.get(2).getX();
        int y2 = pointList.get(2).getY();
        int x3 = pointList.get(1).getX();
        int y3 = pointList.get(1).getY();
        int x4 = pointList.get(3).getX();
        int y4 = pointList.get(3).getY();

        int d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (d == 0) return null;

        int xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
        int yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

        return new IntPoint(xi, yi);
    }

    /**
     * Computes the intersection between two lines. pointList should have points in correct order
     */
    private static float[] calcIntersection(float[] coordinates) {
        float x1 = coordinates[0];
        float y1 = coordinates[1];
        float x2 = coordinates[2];
        float y2 = coordinates[3];
        float x3 = coordinates[4];
        float y3 = coordinates[5];
        float x4 = coordinates[6];
        float y4 = coordinates[7];

        float d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (d == 0) return null;

        float xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
        float yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

        return new float[]{xi, yi};
    }
}
