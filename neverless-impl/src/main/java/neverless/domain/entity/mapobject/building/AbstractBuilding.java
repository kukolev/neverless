package neverless.domain.entity.mapobject.building;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.PlatformShape;
import neverless.domain.entity.mapobject.AbstractPhysicalObject;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractBuilding extends AbstractPhysicalObject {

    @Override
    public PlatformShape getPlatformShape() {
        return PlatformShape.CUSTOM;
    }
}
