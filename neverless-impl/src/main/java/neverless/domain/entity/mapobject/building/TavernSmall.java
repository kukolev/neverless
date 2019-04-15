package neverless.domain.entity.mapobject.building;

import neverless.Direction;
import neverless.Signatures;
import neverless.domain.entity.mapobject.Coordinate;

import java.util.ArrayList;
import java.util.List;


public final class TavernSmall extends AbstractBuilding {

    {
        setHeight(813);
        setWidth(500);
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate().setX(39).setY(0));
        coordinates.add(new Coordinate().setX(196).setY(-150));
        coordinates.add(new Coordinate().setX(-250).setY(-150));
        coordinates.add(new Coordinate().setX(-250).setY(0));
        setPlatformCoordinates(coordinates);
        setDirection(Direction.NORTH);
    }


    @Override
    public int getPlatformWidth() {
        return 5;
    }

    @Override
    public int getPlatformHeight() {
        return 4;
    }

    @Override
    public String getSignature() {
        return Signatures.IMG_TAVERN_SMALL;
    }
}