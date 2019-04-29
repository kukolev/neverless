package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.util.PlatformShape;
import neverless.util.Signatures;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Player extends AbstractLiveObject {

    @Override
    public String getSignature() {
        return Signatures.IMG_PLAYER;
    }

    @Override
    public PlatformShape getPlatformShape() {
        return PlatformShape.ELLIPSE;
    }

}