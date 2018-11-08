package neverless.domain.entity.mapobject.wall;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public final class StoneWall extends AbstractWall {

    @Override
    public String getSignature() {
        return "STONE_WALL_";
    }
}
