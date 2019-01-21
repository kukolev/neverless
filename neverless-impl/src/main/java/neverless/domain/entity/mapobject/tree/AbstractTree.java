package neverless.domain.entity.mapobject.tree;

import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.MapObjectMetaType;


public abstract class AbstractTree extends AbstractMapObject {

    @Override
    public MapObjectMetaType getMetaType() {
        return MapObjectMetaType.IMPASSIBLE_TERRAIN;
    }
}
