package neverless.domain.mapobject.wall;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.mapobject.AbstractMapObject;

@Data
@Accessors(chain = true)
public class AbstractWall extends AbstractMapObject {

    public AbstractWall() {
        setWidth(1);
        setHeight(1);
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
