package neverless.domain.entity.mapobject.building;

import neverless.Signatures;


public final class LargeVillageHouse extends AbstractBuilding {

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
        return Signatures.IMG_HOUSE_LARGE;
    }
}