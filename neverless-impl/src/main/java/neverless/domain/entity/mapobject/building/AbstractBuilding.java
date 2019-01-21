package neverless.domain.entity.mapobject.building;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.PlatformShape;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.MapObjectMetaType;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractBuilding extends AbstractMapObject {

    @Override
    public MapObjectMetaType getMetaType() {
        return MapObjectMetaType.IMPASSIBLE_TERRAIN;
    }

    @Override
    public PlatformShape getPlatformShape() {
        return PlatformShape.CUSTOM;
    }
}
