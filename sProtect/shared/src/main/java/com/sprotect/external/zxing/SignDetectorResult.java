package com.sprotect.external.zxing;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.PerspectiveTransform;

/**
 * result detecting of sign
 */
public class SignDetectorResult {
    private static final String DELIMITER = ";";

    private DetectorResult detectorResult;

    public SignDetectorResult(DetectorResult detectorResult) {
        this.detectorResult = detectorResult;
    }

    public PerspectiveTransform calcTransform() {
        return createRevertedTransform(getTopLeft(), getTopRight(), getBottomLeft(), getAlignmentPattern(),
                detectorResult.getDimension());
    }

    public ResultPoint getBottomLeft() {
        return detectorResult.getPoints()[0];
    }

    public ResultPoint getTopLeft() {
        return detectorResult.getPoints()[1];
    }

    public ResultPoint getTopRight() {
        return detectorResult.getPoints()[2];
    }

    public ResultPoint getAlignmentPattern() {
        return detectorResult.getPoints()[3];
    }

    private static PerspectiveTransform createRevertedTransform(ResultPoint topLeft,
                                                                ResultPoint topRight,
                                                                ResultPoint bottomLeft,
                                                                ResultPoint alignmentPattern,
                                                                int dimension) {
        float dimMinusThree = (float) dimension - 3.5f;
        float bottomRightX;
        float bottomRightY;
        float sourceBottomRightX;
        float sourceBottomRightY;
        if (alignmentPattern != null) {
            bottomRightX = alignmentPattern.getX();
            bottomRightY = alignmentPattern.getY();
            sourceBottomRightX = sourceBottomRightY = dimMinusThree - 3.0f;
        } else {
            // Don't have an alignment pattern, just make up the bottom-right point
            bottomRightX = (topRight.getX() - topLeft.getX()) + bottomLeft.getX();
            bottomRightY = (topRight.getY() - topLeft.getY()) + bottomLeft.getY();
            sourceBottomRightX = sourceBottomRightY = dimMinusThree;
        }

        return PerspectiveTransform.quadrilateralToQuadrilateral(
                topLeft.getX(),
                topLeft.getY(),
                topRight.getX(),
                topRight.getY(),
                bottomRightX,
                bottomRightY,
                bottomLeft.getX(),
                bottomLeft.getY(),

                3.5f,
                3.5f,
                dimMinusThree,
                3.5f,
                sourceBottomRightX,
                sourceBottomRightY,
                3.5f,
                dimMinusThree);
    }

    public boolean isValid() {
        return (detectorResult.getPoints().length == 4);
    }

    public String serializeToString() {
        StringBuilder serializedBuilder = new StringBuilder();
        for (ResultPoint rp : detectorResult.getPoints()) {
            serializedBuilder.append(rp.getX()).append(DELIMITER);
            serializedBuilder.append(rp.getY()).append(DELIMITER);
        }
        serializedBuilder.append(detectorResult.getDimension()).append(DELIMITER);

        return serializedBuilder.toString();
    }

    public static SignDetectorResult fromString(String serializedString) {
        String[] dataStrings = serializedString.split(DELIMITER);
        ResultPoint[] points = new ResultPoint[4];
        int dimension;
        assert (dataStrings.length == 9);

        for (int i = 0; i < 8; i+=2) {
            points[i / 2] = new ResultPoint(Float.valueOf(dataStrings[i]), Float.valueOf(dataStrings[i + 1]));
        }
        dimension = Integer.valueOf(dataStrings[8]);

        return new SignDetectorResult(new DetectorResult(dimension, points));
    }
}
