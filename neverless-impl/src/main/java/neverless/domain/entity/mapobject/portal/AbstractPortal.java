package neverless.domain.entity.mapobject.portal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.Signatures;
import neverless.domain.entity.Location;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.MapObjectMetaType;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AbstractPortal extends AbstractMapObject {

    private Location destination;
    private Integer destX;
    private Integer destY;

    @Override
    public String getSignature() {
        return Signatures.IMG_PORTAL;
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public MapObjectMetaType getMetaType() {
        return MapObjectMetaType.PORTAL;
    }
}
