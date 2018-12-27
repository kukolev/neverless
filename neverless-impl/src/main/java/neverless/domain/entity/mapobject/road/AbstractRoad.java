package neverless.domain.entity.mapobject.road;

import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.MapObjectMetaType;

import javax.persistence.Entity;

@Entity
public abstract class AbstractRoad extends AbstractMapObject {

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public MapObjectMetaType getMetaType() {
        return MapObjectMetaType.TERRAIN;
    }
}
