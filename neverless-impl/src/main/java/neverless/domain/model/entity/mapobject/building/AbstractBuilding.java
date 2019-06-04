package neverless.domain.model.entity.mapobject.building;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.PlatformShape;
import neverless.domain.model.entity.mapobject.AbstractPhysicalObject;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractBuilding extends AbstractPhysicalObject {

    @Override
    public PlatformShape getPlatformShape() {
        return PlatformShape.CUSTOM;
    }

    @Override
    public boolean isHighlightable() {
        return false;
    }
}
