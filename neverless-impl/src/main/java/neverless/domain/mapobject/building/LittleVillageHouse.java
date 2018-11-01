package neverless.domain.mapobject.building;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class LittleVillageHouse extends AbstractBuilding {

    @Override
    public String getSignature() {
        return "BUILDING_001_";
    }

    @Override
    public int getWidth() {
        return 2;
    }

    @Override
    public int getHeight() {
        return 2;
    }
}