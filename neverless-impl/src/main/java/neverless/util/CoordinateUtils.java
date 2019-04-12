package neverless.util;

import neverless.Direction;
import neverless.domain.entity.mapobject.Coordinate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static neverless.Constants.LOCAL_MAP_STEP_LENGTH;
import static neverless.Direction.EAST;
import static neverless.Direction.NORTH;
import static neverless.Direction.NORTH_EAST;
import static neverless.Direction.NORTH_WEST;
import static neverless.Direction.SOUTH;
import static neverless.Direction.SOUTH_EAST;
import static neverless.Direction.SOUTH_WEST;
import static neverless.Direction.WEST;

public class CoordinateUtils {

    private static final float SIN_225 = 0.383f;
    private static final float SIN_675 = 0.924f;
    private static final float SIN_900 = 1;

    /**
     * Calculates and returns coordinates in direction, defined by target coordinates.
     *
     * @param startX   start horizontal coordinate.
     * @param startY   start vertical coordinate.
     * @param targetX   finish horizontal coordinate.
     * @param targetY   finish vertical coordinate.
     */
    public static Coordinate calcNextStep(int startX, int startY, int targetX, int targetY) {

        double length = sqrt(pow(targetX - startX, 2) + pow(targetY - startY, 2));

        double sin = (targetY - startY) / length;
        double cos = (targetX - startX) / length;

        int newX;
        int newY;

        if (length > LOCAL_MAP_STEP_LENGTH) {
            newX = startX + (int) (LOCAL_MAP_STEP_LENGTH * cos);
            newY = startY + (int) (LOCAL_MAP_STEP_LENGTH * sin);
        } else {
            newX = targetX;
            newY = targetY;
        }

        return new Coordinate()
                .setX(newX)
                .setY(newY);
    }

    /**
     * Returns true if length of segment, defined by coordinates less than value.
     *
     * @param x1        first horizontal coordinate.
     * @param y1        first vertical coordinate.
     * @param x2        second horizontal coordinate.
     * @param y2        second vertical coordinate.
     * @param range     range for compare.
     */
    public static boolean isCoordinatesInRange(int x1, int y1, int x2, int y2, int range) {
        double realRange = sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2));
        return realRange <= range;
    }

    /**
     * Calculates and returns direction relative to (x1,y1) point of view.
     *
     * @param x1        first horizontal coordinate.
     * @param y1        first vertical coordinate.
     * @param x2        second horizontal coordinate.
     * @param y2        second vertical coordinate.
     */
    public static Direction calcDirection(int x1, int y1, int x2, int y2) {
        double distance = sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
        double sinus = (y2 - y1) / distance;

        if (x2 > x1) {
            if (sinus > -SIN_900 && sinus < -SIN_675) {
                return NORTH;
            } else if (sinus > -SIN_675 && sinus <= -SIN_225) {
                return NORTH_EAST;
            } else if (sinus > -SIN_225 && sinus <= SIN_225) {
                return EAST;
            } else if (sinus > SIN_225 && sinus <= SIN_675) {
                return SOUTH_EAST;
            } else {
                return SOUTH;
            }
        } else {
            if (sinus < -SIN_675) {
                return NORTH;
            } else if (sinus >= -SIN_675 && sinus < -SIN_225) {
                return NORTH_WEST;
            } else if (sinus >= -SIN_225 && sinus < SIN_225) {
                return WEST;
            } else if (sinus >= SIN_225 && sinus < SIN_675) {
                return SOUTH_WEST;
            } else {
                return SOUTH;
            }
        }
    }

    /**
     * Returns true if two ellipses have one or more common points.
     *
     * @param x1 horizontal coordinate of first ellipse center.
     * @param y1 vertical coordinate of first ellipse center.
     * @param a1 horizontal radius of first ellipse.
     * @param b1 vertical radius of first ellipse.
     * @param x2 horizontal coordinate of second ellipse center.
     * @param y2 vertical coordinate of second ellipse center.
     * @param a2 horizontal radius of second ellipse.
     * @param b2 vertical radius of second ellipse.
     */
    public static boolean isCurvesIntersected(int x1, int y1, int a1, int b1, int x2, int y2, int a2, int b2) {
        double distance = sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
        double sinus = abs((y2 - y1) / distance);

        double radius1 = sqrt(a1 * a1 - sinus * sinus * (a1 * a1 - b1 * b1));
        double radius2 = sqrt(a2 * a2 - sinus * sinus * (a2 * a2 - b2 * b2));

        return radius1 + radius2 >= distance;
    }

    /**
     * Returns true if two curves crossed i.e. has at least one common points
     *
     * @param firstCoordinates  list of coordinates for approximate first curve.
     * @param secondCoordinates list of coordinates for approximate second curve.
     */
    public static boolean isCurvesIntersected(List<Coordinate> firstCoordinates, List<Coordinate> secondCoordinates) {
        for (int i = 0; i < firstCoordinates.size(); i++) {
            for (int j = 0; j < secondCoordinates.size(); j++) {
                Coordinate firstPoint1 = firstCoordinates.get(i);
                Coordinate firstPoint2 = (i < firstCoordinates.size() - 1) ? firstCoordinates.get(i + 1) : firstCoordinates.get(0);
                Coordinate secondPoint1 = secondCoordinates.get(j);
                Coordinate secondPoint2 = (j < secondCoordinates.size() - 1) ? secondCoordinates.get(j + 1) : secondCoordinates.get(0);

                boolean isCrossed = isSegmentsIntersected(firstPoint1, firstPoint2, secondPoint1, secondPoint2);
                if (isCrossed) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if segment and curve intersected.
     *
     * @param x1                first segment horizontal coordinate.
     * @param y1                first segment vertical coordinate.
     * @param x2                second segment horizontal coordinate.
     * @param y2                second segment vertical coordinate.
     * @param curveCoordinates  list of coordinates for curve approximation.
     */
    public static boolean isSegmentAndCurveIntersected(int x1, int y1, int x2, int y2, List<Coordinate> curveCoordinates) {
        Coordinate segmentPoint1 = new Coordinate()
                .setX(x1)
                .setY(y1);
        Coordinate segmentPoint2 = new Coordinate()
                .setX(x2)
                .setY(y2);
        List<Coordinate> segmentCoordinates = new ArrayList<>();
        segmentCoordinates.add(segmentPoint1);
        segmentCoordinates.add(segmentPoint2);

        return isCurvesIntersected(segmentCoordinates, curveCoordinates);
    }

    /**
     * Return true if ellipse crossed with curve.
     *
     * @param ellipseX         horizontal coordinate of the ellipse.
     * @param ellipseY         vertical coordinate of the ellipse.
     * @param radiusX          horizontal radius of the ellipse.
     * @param radiusY          vertical radius of the ellipse.
     * @param curveCoordinates list of coordinates for approximate the curve.
     */
    public static boolean isCurvesIntersected(int ellipseX, int ellipseY, int radiusX, int radiusY, List<Coordinate> curveCoordinates) {
        for (int i = 0; i < curveCoordinates.size(); i++) {
            Coordinate firstPoint1 = curveCoordinates.get(i);
            Coordinate firstPoint2 = (i < curveCoordinates.size() - 1) ? curveCoordinates.get(i + 1) : curveCoordinates.get(0);

            double x1 = firstPoint1.getX() - ellipseX;
            double x2 = firstPoint2.getX() - ellipseX;
            double y1 = firstPoint1.getY() - ellipseY;
            double y2 = firstPoint2.getY() - ellipseY;

            double tg = (y2 - y1) / (x2 - x1);
            double lineConst = y1 - (x1 * tg);

            double A = tg * tg * radiusX * radiusX + radiusY * radiusY;
            double B = 2 * lineConst * tg * radiusX * radiusX;
            double C = lineConst * lineConst * radiusX * radiusX - radiusX * radiusX * radiusY * radiusY;

            double D = B * B - 4 * A * C;
            if (D >= 0) {
                double interX1 = (-B + sqrt(D)) / (2 * A);
                double interX2 = (-B - sqrt(D)) / (2 * A);

                double maxX = Math.max(x1, x2);
                double minX = Math.min(x1, x2);

                boolean isIntersection =
                        ((interX1 >= minX) &&
                                (interX1 <= maxX)) ||
                                ((interX2 >= minX) &&
                                        (interX2 <= maxX));

                if (isIntersection) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if two line segments crossed.
     *
     * @param firstPoint1  start coordinates of first line segment.
     * @param firstPoint2  finish coordinates of first line segment.
     * @param secondPoint1 start coordinates of first second segment.
     * @param secondPoint2 finish coordinates of first second segment.
     */
    public static boolean isSegmentsIntersected(Coordinate firstPoint1, Coordinate firstPoint2, Coordinate secondPoint1, Coordinate secondPoint2) {
        int ax1 = firstPoint1.getX();
        int ax2 = firstPoint2.getX();
        int ay1 = firstPoint1.getY();
        int ay2 = firstPoint2.getY();

        int bx1 = secondPoint1.getX();
        int bx2 = secondPoint2.getX();
        int by1 = secondPoint1.getY();
        int by2 = secondPoint2.getY();

        int v1 = (bx2 - bx1) * (ay1 - by1) - (by2 - by1) * (ax1 - bx1);
        int v2 = (bx2 - bx1) * (ay2 - by1) - (by2 - by1) * (ax2 - bx1);
        int v3 = (ax2 - ax1) * (by1 - ay1) - (ay2 - ay1) * (bx1 - ax1);
        int v4 = (ax2 - ax1) * (by2 - ay1) - (ay2 - ay1) * (bx2 - ax1);

        return (v1 * v2 < 0) && (v3 * v4 < 0);
    }
}