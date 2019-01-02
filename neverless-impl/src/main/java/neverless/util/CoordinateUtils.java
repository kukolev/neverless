package neverless.util;

import neverless.domain.entity.mapobject.Coordinate;
import neverless.dto.screendata.CoordinateDto;
import neverless.dto.command.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static neverless.dto.command.Direction.DOWN;
import static neverless.dto.command.Direction.DOWN_LEFT;
import static neverless.dto.command.Direction.DOWN_RIGHT;
import static neverless.dto.command.Direction.LEFT;
import static neverless.dto.command.Direction.RIGHT;
import static neverless.dto.command.Direction.UP;
import static neverless.dto.command.Direction.UP_LEFT;
import static neverless.dto.command.Direction.UP_RIGHT;

public class CoordinateUtils {

    private final static String LINE_UP = "+";
    private final static String LINE_JUMP = "\\";


    public static CoordinateDto getNextCoordinatesForLos(int playerX, int playerY, int enemyX, int enemyY) {
        // draw LoS to player
        int deltaX = playerX - enemyX;
        int deltaY = playerY - enemyY;

        double dX;
        double dY;
        int directionX = deltaX != 0 ? deltaX / abs(deltaX) : 0;
        int directionY = deltaY != 0 ? deltaY / abs(deltaY) : 0;

        if (abs(deltaX) > abs(deltaY)) {
            dX = 1;
            dY = ceil(abs(deltaY / deltaX));
        } else {
            dY = 1;
            dX = ceil(abs(deltaX / deltaY));
        }

        return new CoordinateDto()
                .setX((int) (enemyX + dX * directionX))
                .setY((int) (enemyY + dY * directionY));
    }

    public static boolean isCoordinatesInRange(int x1, int y1, int x2, int y2, int range) {
        double realRange = sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2));
        return realRange <= range;
    }

    /**
     * Returns list direction steps for line between two points.
     * It's a facade method, main calculations are placed below in methods "mapLineActionsList" and "commonLine".
     *
     * @param x1 horizontal start coordinate.
     * @param y1 vertical start coordinate.
     * @param x2 horizontal finish coordinate.
     * @param y2 vertical finish coordinate.
     */
    public static List<Direction> line(int x1, int y1, int x2, int y2) {

        List<String> actions = new ArrayList<>();

        int dx = x2 - x1;
        int dy = y2 - y1;

        if (x2 >= x1 && y2 >= y1) {
            actions = commonLine(dx, dy);
            if (abs(dx) > abs(dy)) {
                return mapLineActionsList(actions, RIGHT, DOWN_RIGHT);
            } else {
                return mapLineActionsList(actions, DOWN, DOWN_RIGHT);
            }
        } else if (x2 < x1 && y2 >= y1) {
            actions = commonLine(-dx, dy);
            if (abs(dx) > abs(dy)) {
                return mapLineActionsList(actions, LEFT, DOWN_LEFT);
            } else {
                return mapLineActionsList(actions, DOWN, DOWN_LEFT);
            }
        } else if (x2 >= x1 && y2 < y1) {
            actions = commonLine(dx, -dy);

            if (abs(dx) > abs(dy)) {
                return mapLineActionsList(actions, RIGHT, UP_RIGHT);
            } else {
                return mapLineActionsList(actions, UP, UP_RIGHT);
            }
        } else {
            actions = commonLine(-dx, -dy);
            if (abs(dx) > abs(dy)) {
                return mapLineActionsList(actions, LEFT, UP_LEFT);
            } else {
                return mapLineActionsList(actions, UP, UP_LEFT);
            }
        }
    }

    /**
     * Converts list of two basic directions to list with result directions.
     *
     * @param actions list of basic directions.
     * @param up      real direction which should replace LINE_UP
     * @param jump    real direction which should replace LINE_JUMP
     */
    private static List<Direction> mapLineActionsList(List<String> actions, Direction up, Direction jump) {
        List<Direction> result = new ArrayList<>();
        return actions
                .stream()
                .map(a -> {
                    switch (a) {
                        case LINE_UP:
                            return up;
                        case LINE_JUMP:
                            return jump;
                    }
                    return up;
                })
                .collect(Collectors.toList());
    }

    /**
     * Returns list of basic directions.
     * There are to basic directions: LINE_UP = "+", LINE_JUMP = "/".
     * Example: dx = 5, dy = 10;
     * Result: [+, /, +, /, +, /, +, /, +, /]
     *
     * @param dx abs of delta between x2 and x1
     * @param dy abs of delta between y2 and y1
     */
    private static List<String> commonLine(int dx, int dy) {
        List<String> result = new ArrayList<>();

        int lenMinor;
        int upRightCount;
        int upCount;

        if (abs(dy) > abs(dx)) {
            lenMinor = abs(dx) + 1;
            upRightCount = lenMinor - 1;
            upCount = abs(dy - upRightCount);
        } else {
            lenMinor = abs(dy) + 1;
            upRightCount = lenMinor - 1;
            upCount = abs(dx - upRightCount);
        }

        if (upRightCount > 0) {
            int[] packs = new int[upRightCount];
            int packValue = upCount / upRightCount;
            for (int i = 0; i < packs.length; i++) {
                packs[i] = packValue;
            }

            int extraValue = upCount % upRightCount;
            if (extraValue > 0) {
                int extraStep = packs.length / extraValue;
                for (int i = 0; i < packs.length / 2; i += extraStep) {
                    if (extraValue > 0) {
                        packs[packs.length - 1 - i]++;
                        extraValue--;
                    }
                    if (extraValue > 0) {
                        packs[i]++;
                        extraValue--;
                    }
                }
            }

            for (int i = 0; i < packs.length; i++) {
                for (int j = 0; j < packs[i]; j++) {
                    result.add(LINE_UP);
                }
                result.add(LINE_JUMP);
            }
        } else {
            for (int i = 0; i < upCount; i++) {
                result.add(LINE_UP);
            }
        }
        return result;
    }

    /**
     * Returns true if two ellipses have one or common points.
     *
     * @param x1 horizontal coordinate of first ellipse center.
     * @param y1 vertical coordinate of first ellipse center.
     * @param a1 horizontal platformWidth of first ellipse.
     * @param b1 vertical platformWidth of first ellipse.
     * @param x2 horizontal coordinate of second ellipse center.
     * @param y2 vertical coordinate of second ellipse center.
     * @param a2 horizontal platformWidth of second ellipse.
     * @param b2 vertical platformWidth of second ellipse.
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

            double a = radiusX;
            double b = radiusY;

            double A = tg * tg * a * a + b * b;
            double B = 2 * lineConst * tg * a * a;
            double C = lineConst * lineConst * a * a - a * a * b * b;

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