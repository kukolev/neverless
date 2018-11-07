package neverless.util;

import org.testng.annotations.Test;

import static neverless.util.CoordinateUtils.getNextCoordinatesForLos;
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
}