package neverless.domain.mapobject.wall;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class StoneWall extends AbstractWall {

    @Override
    public String getSignature() {
        return "STONE_WALL_";
    }
}
