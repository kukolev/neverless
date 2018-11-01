package neverless.domain.mapobject.building;

public final class LongVillageHouse extends AbstractBuilding {

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 7;
    }

    @Override
    public String getSignature() {
        return "BUILDING_003_";
    }
}
