package neverless.domain.mapobject.portal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LocationsPortal extends AbstractPortal{

    @Override
    public String getSignature() {
        return "DOOR_DUNGEON";
    }
}
