package neverless.domain.entity.mapobject.portal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.entity.Location;
import neverless.domain.entity.mapobject.AbstractArea;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LocationPortal extends AbstractArea {

    private Location destination;
    private Integer destX;
    private Integer destY;
}
