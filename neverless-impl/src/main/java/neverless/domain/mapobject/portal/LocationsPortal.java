package neverless.domain.mapobject.portal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LocationsPortal extends AbstractPortal{

    public LocationsPortal() {
        setHeight(1);
        setWidth(1);
        setSignature("DOOR_DUNGEON");
    }
}
