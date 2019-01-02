package neverless.domain.entity.mapobject.building;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Resources;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public final class LittleVillageHouse extends AbstractBuilding {

    @Override
    public String getSignature() {
        return Resources.IMG_HOUSE_LITTLE;
    }

    @Override
    public int getPlatformWidth() {
        return 2;
    }

    @Override
    public int getPlatformHeight() {
        return 2;
    }
}