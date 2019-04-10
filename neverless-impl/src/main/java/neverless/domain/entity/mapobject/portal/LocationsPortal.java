package neverless.domain.entity.mapobject.portal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.Signatures;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LocationsPortal extends AbstractPortal{

    @Override
    public String getSignature() {
        return Signatures.IMG_PORTAL;
    }
}
