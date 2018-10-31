package neverless.domain.mapobject.building;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class LittleVillageHouse extends AbstractBuilding {

    public LittleVillageHouse() {
        super();
        setHeight(2);
        setWidth(2);
        setSignature("BUILDING_001_");
    }
}