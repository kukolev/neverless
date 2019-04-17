package neverless.domain.entity.mapobject.portal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.entity.Location;
import neverless.domain.entity.mapobject.AbstractArea;
import neverless.domain.entity.mapobject.Coordinate;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LocationPortal extends AbstractArea {

    private Coordinate enterPoint;
    private Location destination;
    private Integer destX;
    private Integer destY;
}
