package neverless.util;

import neverless.domain.entity.mapobject.Coordinate;
import neverless.dto.CoordinateDto;
import neverless.Direction;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static neverless.Direction.DOWN;
import static neverless.Direction.DOWN_RIGHT;
import static neverless.util.CoordinateUtils.getNextCoordinatesForLos;
import static neverless.util.CoordinateUtils.isCurvesIntersected;
import static neverless.util.CoordinateUtils.line;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CoordinateUtilsTest {

    @Test
    public void testPlayer1010Enemy2020() {
        CoordinateDto coordinate = getNextCoordinatesForLos(10, 10, 20, 20);
        assertEquals(coordinate.getX(), 19);
        assertEquals(coordinate.getY(), 19);
    }

    @Test
    public void testPlayer2020Enemy1010() {
        CoordinateDto coordinate = getNextCoordinatesForLos(20, 20, 10, 10);
        assertEquals(coordinate.getX(), 11);
        assertEquals(coordinate.getY(), 11);
    }

    @Test
    public void testPlayer1010Enemy1020() {
        CoordinateDto coordinate = getNextCoordinatesForLos(10, 10, 10, 20);
        assertEquals(coordinate.getX(), 10);
        assertEquals(coordinate.getY(), 19);
    }

    @Test
    public void testPlayer1010Enemy2010() {
        CoordinateDto coordinate = getNextCoordinatesForLos(10, 10, 20, 10);
        assertEquals(coordinate.getX(), 19);
        assertEquals(coordinate.getY(), 10);
    }

    @Test
    public void testPlayer1010Enemy1012() {
        CoordinateDto coordinate = getNextCoordinatesForLos(10, 10, 10, 12);
        assertEquals(coordinate.getX(), 10);
        assertEquals(coordinate.getY(), 11);
    }

    @Test
    public void testLine() {
        List<Direction> directions = line(0, 0, 2, 5);
        assertEquals(directions.get(0), DOWN);
        assertEquals(directions.get(1), DOWN_RIGHT);
        assertEquals(directions.get(2), DOWN);
        assertEquals(directions.get(3), DOWN);
        assertEquals(directions.get(4), DOWN_RIGHT);
    }

    @Test
    public void testIsCurveIntersectedTwoEllipses() {
        assertFalse(CoordinateUtils.isCurvesIntersected(10, 10, 4, 4, 20, 20, 5, 5));
        assertTrue(CoordinateUtils.isCurvesIntersected(10, 10, 4, 4, 20, 10, 7, 7));
    }

    @Test
    public void testIsCurveIntersectedEllipseAndCurvePositive() {
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate().setX(0).setY(0));
        coordinates.add(new Coordinate().setX(10).setY(0));

        assertTrue(isCurvesIntersected(0, 0, 4, 8, coordinates));
    }

    @Test
    public void testIsCurveIntersectedEllipseAndCurveNegative() {
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate().setX(0).setY(5));
        coordinates.add(new Coordinate().setX(100).setY(15));

        assertFalse(isCurvesIntersected(0, 0, 8, 4, coordinates));
    }

    @Test
    public void testIsCurveIntersectedTwoCurvesPositive() {
        List<Coordinate> firstCoordinates = new ArrayList<>();
        firstCoordinates.add(new Coordinate().setX(0).setY(0));
        firstCoordinates.add(new Coordinate().setX(10).setY(0));
        firstCoordinates.add(new Coordinate().setX(10).setY(10));
        firstCoordinates.add(new Coordinate().setX(0).setY(10));

        List<Coordinate> secondCoordinates = new ArrayList<>();
        secondCoordinates.add(new Coordinate().setX(5).setY(5));
        secondCoordinates.add(new Coordinate().setX(15).setY(5));
        secondCoordinates.add(new Coordinate().setX(15).setY(15));
        secondCoordinates.add(new Coordinate().setX(5).setY(15));

        assertTrue(CoordinateUtils.isCurvesIntersected(firstCoordinates, secondCoordinates));
        assertTrue(CoordinateUtils.isCurvesIntersected(secondCoordinates, firstCoordinates));
    }

    @Test
    public void testIsCurveIntersectedTwoCurvesNegative() {
        List<Coordinate> firstCoordinates = new ArrayList<>();
        firstCoordinates.add(new Coordinate().setX(0).setY(0));
        firstCoordinates.add(new Coordinate().setX(10).setY(0));
        firstCoordinates.add(new Coordinate().setX(10).setY(10));
        firstCoordinates.add(new Coordinate().setX(0).setY(10));

        List<Coordinate> secondCoordinates = new ArrayList<>();
        secondCoordinates.add(new Coordinate().setX(105).setY(105));
        secondCoordinates.add(new Coordinate().setX(115).setY(105));
        secondCoordinates.add(new Coordinate().setX(115).setY(115));
        secondCoordinates.add(new Coordinate().setX(105).setY(115));

        assertFalse(CoordinateUtils.isCurvesIntersected(firstCoordinates, secondCoordinates));
        assertFalse(CoordinateUtils.isCurvesIntersected(secondCoordinates, firstCoordinates));
    }
}