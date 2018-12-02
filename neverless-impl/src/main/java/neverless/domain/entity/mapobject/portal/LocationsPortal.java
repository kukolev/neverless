package neverless.domain.entity.mapobject.portal;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Resources;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public class LocationsPortal extends AbstractPortal{

    @Override
    public String getSignature() {
        return Resources.IMG_PORTAL;
    }
}
