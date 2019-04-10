package neverless.domain.entity.mapobject.building;

import neverless.Signatures;

public final class LongVillageHouse extends AbstractBuilding {

    @Override
    public int getPlatformWidth() {
        return 3;
    }

    @Override
    public int getPlatformHeight() {
        return 7;
    }

    @Override
    public String getSignature() {
        return Signatures.IMG_HOUSE_LONG;
    }
}
