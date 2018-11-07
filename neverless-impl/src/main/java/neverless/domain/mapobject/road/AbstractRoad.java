package neverless.domain.mapobject.road;

import neverless.domain.mapobject.AbstractMapObject;

import javax.persistence.Entity;

@Entity
public abstract class AbstractRoad extends AbstractMapObject {

    @Override
    public boolean isPassable() {
        return true;
    }
}
