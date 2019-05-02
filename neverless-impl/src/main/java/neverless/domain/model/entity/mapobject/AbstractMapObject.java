package neverless.domain.model.entity.mapobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.AbstractGameObject;
import neverless.domain.model.entity.Location;
import neverless.util.Coordinate;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AbstractMapObject extends AbstractGameObject {

    private Integer x;
    private Integer y;
    private Location location;
    private List<Coordinate> platformCoordinates = new ArrayList<>();
}
