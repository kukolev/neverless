package neverless.domain.entity.mapobject.building;

import neverless.Resouces;

import javax.persistence.Entity;

@Entity
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
        return Resouces.IMG_HOUSE_LARGE;
    }
}