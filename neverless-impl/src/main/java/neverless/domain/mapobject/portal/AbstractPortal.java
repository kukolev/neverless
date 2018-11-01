package neverless.domain.mapobject.portal;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.mapobject.AbstractMapObject;

@Data
@Accessors(chain = true)
public class AbstractPortal extends AbstractMapObject {

    private String destination;
    private Integer destX;
    private Integer destY;

    @Override
    public String getSignature() {
        return "PORTAL_";
    }

    @Override
    public boolean isPassable() {
        return true;
    }
}
