package neverless.domain.model.entity.mapobject.portal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.Location;
import neverless.domain.model.entity.mapobject.AbstractMapArea;
import neverless.util.Coordinate;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LocationPortal extends AbstractMapArea {

    private Coordinate enterPoint;
    private Location destination;
    private Integer destX;
    private Integer destY;
}
