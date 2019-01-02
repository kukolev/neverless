package neverless.domain.entity.mapobject.building;

import neverless.Resources;

import javax.persistence.Entity;

@Entity
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
        return Resources.IMG_HOUSE_LONG;
    }
}
