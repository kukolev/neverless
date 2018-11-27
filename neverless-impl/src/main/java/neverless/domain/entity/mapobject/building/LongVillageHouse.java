package neverless.domain.entity.mapobject.building;

import neverless.Resouces;

import javax.persistence.Entity;

@Entity
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
        return Resouces.IMG_HOUSE_LONG;
    }
}
