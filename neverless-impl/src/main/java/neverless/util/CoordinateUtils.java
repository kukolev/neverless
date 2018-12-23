package neverless.util;

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


    public static Coordinate getNextCoordinatesForLos(int playerX, int playerY, int enemyX, int enemyY) {
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

        return new Coordinate()
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
     * @param x1    horizontal start coordinate.
     * @param y1    vertical start coordinate.
     * @param x2    horizontal finish coordinate.
     * @param y2    vertical finish coordinate.
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
     * @param actions   list of basic directions.
     * @param up        real direction which should replace LINE_UP
     * @param jump      real direction which should replace LINE_JUMP
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
     * @param dx    abs of delta between x2 and x1
     * @param dy    abs of delta between y2 and y1
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
        return result;
    }
}
