package neverless.domain.entity.mapobject.wall;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.MapObjectMetaType;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractWall extends AbstractMapObject {

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public MapObjectMetaType getMetaType() {
        return MapObjectMetaType.IMPASSIBLE_TERRAIN;
    }
}
