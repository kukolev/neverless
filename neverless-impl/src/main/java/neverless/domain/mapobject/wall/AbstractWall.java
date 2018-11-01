package neverless.domain.mapobject.wall;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.mapobject.AbstractMapObject;

@Data
@Accessors(chain = true)
public abstract class AbstractWall extends AbstractMapObject {

    @Override
    public boolean isPassable() {
        return false;
    }
}
