package neverless.domain.entity.mapobject.building;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
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