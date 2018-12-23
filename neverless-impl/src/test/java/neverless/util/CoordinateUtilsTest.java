package neverless.util;

import neverless.dto.command.Direction;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static neverless.dto.command.Direction.DOWN;
import static neverless.dto.command.Direction.DOWN_LEFT;
import static neverless.dto.command.Direction.DOWN_RIGHT;
import static neverless.dto.command.Direction.UP;
import static neverless.dto.command.Direction.UP_LEFT;
import static neverless.dto.command.Direction.UP_RIGHT;
import static neverless.util.CoordinateUtils.getNextCoordinatesForLos;
import static neverless.util.CoordinateUtils.line;
import static org.testng.Assert.assertEquals;

public class CoordinateUtilsTest {

    @Test
    public void textPlayer1010Enemy2020() {
        Coordinate coordinate = getNextCoordinatesForLos(10, 10, 20, 20);
        assertEquals(coordinate.getX(), 19);
        assertEquals(coordinate.getY(), 19);
    }

    @Test
    public void textPlayer2020Enemy1010() {
        Coordinate coordinate = getNextCoordinatesForLos(20, 20, 10, 10);
        assertEquals(coordinate.getX(), 11);
        assertEquals(coordinate.getY(), 11);
    }

    @Test
    public void textPlayer1010Enemy1020() {
        Coordinate coordinate = getNextCoordinatesForLos(10, 10, 10, 20);
        assertEquals(coordinate.getX(), 10);
        assertEquals(coordinate.getY(), 19);
    }

    @Test
    public void textPlayer1010Enemy2010() {
        Coordinate coordinate = getNextCoordinatesForLos(10, 10, 20, 10);
        assertEquals(coordinate.getX(), 19);
        assertEquals(coordinate.getY(), 10);
    }

    @Test
    public void textPlayer1010Enemy1012() {
        Coordinate coordinate = getNextCoordinatesForLos(10, 10, 10, 12);
        assertEquals(coordinate.getX(), 10);
        assertEquals(coordinate.getY(), 11);
    }

    @Test
    public void testLine() {
        List<Direction> directions = line(0, 0, 2, 5);
        assertEquals(directions.get(0), UP);
        assertEquals(directions.get(1), UP_RIGHT);
        assertEquals(directions.get(2), UP);
        assertEquals(directions.get(3), UP);
        assertEquals(directions.get(4), UP_RIGHT);
    }
}