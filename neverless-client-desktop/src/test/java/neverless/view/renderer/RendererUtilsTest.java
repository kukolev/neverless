package neverless.view.renderer;

import neverless.util.Coordinate;
import neverless.PlatformShape;
import neverless.view.domain.Sprite;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static neverless.view.drawer.DrawerUtils.calcRenderOrder;
import static org.testng.Assert.assertTrue;

public class RendererUtilsTest {


    /**
     * Checks that list returned by DrawerUtils::calcRenderOrder is sorted properly.
     * Geometry interpretation of the scene:
     *
     *      8
     *      7
     * 222     111
     * 222   111
     *     111   6666
     *   111   333333333
     * 111 5     4
     *
     * 1 ... 8 - some elements on the scene.
     *
     * One of expected result: 8, 7, 2, 1, 5, 6, 3, 4
     *
     */
    @Test
    private void testCalcRenderOrder() {
        Sprite sprite1 = new Sprite(null)
                .setX(90)
                .setY(60)
                .setPlatformShape(PlatformShape.CUSTOM);
        List<Coordinate> coordinates1 = new ArrayList<>();
        coordinates1.add(new Coordinate().setX(-60).setY(40));
        coordinates1.add(new Coordinate().setX(60).setY(-20));
        coordinates1.add(new Coordinate().setX(50).setY(-40));
        coordinates1.add(new Coordinate().setX(-70).setY(20));
        sprite1.setPlatformCoordinates(coordinates1);

        Sprite sprite2 = new Sprite(null)
                .setX(20)
                .setY(50)
                .setPlatformShape(PlatformShape.CUSTOM);
        List<Coordinate> coordinates2 = new ArrayList<>();
        coordinates2.add(new Coordinate().setX(-10).setY(10));
        coordinates2.add(new Coordinate().setX(10).setY(10));
        coordinates2.add(new Coordinate().setX(10).setY(-10));
        coordinates2.add(new Coordinate().setX(-10).setY(-10));
        sprite2.setPlatformCoordinates(coordinates2);

        Sprite sprite3 = new Sprite(null)
                .setX(135)
                .setY(85)
                .setPlatformShape(PlatformShape.CUSTOM);
        List<Coordinate> coordinates3 = new ArrayList<>();
        coordinates3.add(new Coordinate().setX(-35).setY(5));
        coordinates3.add(new Coordinate().setX(35).setY(5));
        coordinates3.add(new Coordinate().setX(35).setY(-5));
        coordinates3.add(new Coordinate().setX(-35).setY(-5));
        sprite3.setPlatformCoordinates(coordinates3);

        Sprite sprite4 = new Sprite(null)
                .setX(115)
                .setY(105)
                .setPlatformShape(PlatformShape.CUSTOM);
        List<Coordinate> coordinates4 = new ArrayList<>();
        coordinates4.add(new Coordinate().setX(-5).setY(5));
        coordinates4.add(new Coordinate().setX(5).setY(5));
        coordinates4.add(new Coordinate().setX(5).setY(-5));
        coordinates4.add(new Coordinate().setX(-5).setY(-5));
        sprite4.setPlatformCoordinates(coordinates4);

        Sprite sprite5 = new Sprite(null)
                .setX(65)
                .setY(105)
                .setPlatformShape(PlatformShape.CUSTOM);
        List<Coordinate> coordinates5 = new ArrayList<>();
        coordinates5.add(new Coordinate().setX(-5).setY(5));
        coordinates5.add(new Coordinate().setX(5).setY(5));
        coordinates5.add(new Coordinate().setX(5).setY(-5));
        coordinates5.add(new Coordinate().setX(-5).setY(-5));
        sprite5.setPlatformCoordinates(coordinates5);

        Sprite sprite6 = new Sprite(null)
                .setX(145)
                .setY(65)
                .setPlatformShape(PlatformShape.CUSTOM);
        List<Coordinate> coordinates6 = new ArrayList<>();
        coordinates6.add(new Coordinate().setX(-15).setY(5));
        coordinates6.add(new Coordinate().setX(15).setY(5));
        coordinates6.add(new Coordinate().setX(15).setY(-5));
        coordinates6.add(new Coordinate().setX(-15).setY(-5));
        sprite6.setPlatformCoordinates(coordinates6);

        Sprite sprite7 = new Sprite(null)
                .setX(85)
                .setY(25)
                .setPlatformShape(PlatformShape.CUSTOM);
        List<Coordinate> coordinates7 = new ArrayList<>();
        coordinates7.add(new Coordinate().setX(-5).setY(5));
        coordinates7.add(new Coordinate().setX(5).setY(5));
        coordinates7.add(new Coordinate().setX(5).setY(-5));
        coordinates7.add(new Coordinate().setX(-5).setY(-5));
        sprite7.setPlatformCoordinates(coordinates7);

        Sprite sprite8 = new Sprite(null)
                .setX(85)
                .setY(5)
                .setPlatformShape(PlatformShape.CUSTOM);
        List<Coordinate> coordinates8 = new ArrayList<>();
        coordinates8.add(new Coordinate().setX(-5).setY(5));
        coordinates8.add(new Coordinate().setX(5).setY(5));
        coordinates8.add(new Coordinate().setX(5).setY(-5));
        coordinates8.add(new Coordinate().setX(-5).setY(-5));
        sprite8.setPlatformCoordinates(coordinates8);


        List<Sprite> sprites = new ArrayList<>();
        sprites.add(sprite1);
        sprites.add(sprite2);
        sprites.add(sprite3);
        sprites.add(sprite4);
        sprites.add(sprite5);
        sprites.add(sprite6);
        sprites.add(sprite7);
        sprites.add(sprite8);

        List<Sprite> orderedSprites = calcRenderOrder(sprites);

        boolean level0 =
                ((orderedSprites.get(0).equals(sprite8) &&
                        orderedSprites.get(1).equals(sprite2)) ||
                        (orderedSprites.get(0).equals(sprite2) &&
                                orderedSprites.get(1).equals(sprite8)));
        boolean level1 = orderedSprites.get(2).equals(sprite7);
        boolean level2 = orderedSprites.get(3).equals(sprite1);
        boolean level3 =
                ((orderedSprites.get(4).equals(sprite5) &&
                        orderedSprites.get(5).equals(sprite6)) ||
                        (orderedSprites.get(4).equals(sprite6) &&
                                orderedSprites.get(5).equals(sprite5)));
        boolean level4 = orderedSprites.get(6).equals(sprite3);
        boolean level5 = orderedSprites.get(7).equals(sprite4);

        assertTrue(level0);
        assertTrue(level1);
        assertTrue(level2);
        assertTrue(level3);
        assertTrue(level4);
        assertTrue(level5);
    }
}
