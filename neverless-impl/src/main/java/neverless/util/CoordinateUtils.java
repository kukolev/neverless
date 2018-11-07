package neverless.util;

import static java.lang.Math.*;

public class CoordinateUtils {

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
}
