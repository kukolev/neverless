package neverless.domain.mapobject.wall;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class StoneWall extends AbstractWall {

    public StoneWall() {
        setSignature("STONE_WALL_");
    }
}
