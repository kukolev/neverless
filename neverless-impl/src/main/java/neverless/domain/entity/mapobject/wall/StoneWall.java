package neverless.domain.entity.mapobject.wall;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.Resources;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public final class StoneWall extends AbstractWall {

    @Override
    public String getSignature() {
        return Resources.IMG_WALL;
    }
}