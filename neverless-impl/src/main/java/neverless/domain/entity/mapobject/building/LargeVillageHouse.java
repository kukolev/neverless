package neverless.domain.entity.mapobject.building;

import neverless.Resources;

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
        return Resources.IMG_HOUSE_LARGE;
    }
}