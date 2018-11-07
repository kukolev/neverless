package neverless.domain.mapobject.wall;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.mapobject.AbstractMapObject;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractWall extends AbstractMapObject {

    @Override
    public boolean isPassable() {
        return false;
    }
}
