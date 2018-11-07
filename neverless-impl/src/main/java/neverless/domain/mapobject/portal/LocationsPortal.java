package neverless.domain.mapobject.portal;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public class LocationsPortal extends AbstractPortal{

    @Override
    public String getSignature() {
        return "DOOR_DUNGEON";
    }
}
