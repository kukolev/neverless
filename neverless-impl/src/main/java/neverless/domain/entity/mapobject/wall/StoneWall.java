package neverless.domain.entity.mapobject.wall;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.Signatures;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public final class StoneWall extends AbstractWall {

    @Override
    public String getSignature() {
        return Signatures.IMG_WALL;
    }
}