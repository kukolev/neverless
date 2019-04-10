package neverless.domain.entity.mapobject.building;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.Signatures;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public final class LittleVillageHouse extends AbstractBuilding {

    @Override
    public String getSignature() {
        return Signatures.IMG_HOUSE_LITTLE;
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