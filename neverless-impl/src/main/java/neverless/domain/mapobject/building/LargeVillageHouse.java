package neverless.domain.mapobject.building;

public final class LargeVillageHouse extends AbstractBuilding {

    @Override
    public int getWidth() {
        return 5;
    }

    @Override
    public int getHeight() {
        return 4;
    }

    @Override
    public String getSignature() {
        return "BUILDING_002_";
    }
}