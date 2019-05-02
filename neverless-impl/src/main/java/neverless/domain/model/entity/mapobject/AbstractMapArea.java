package neverless.domain.model.entity.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.AbstractGameObject;
import neverless.util.Coordinate;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class AbstractMapArea extends AbstractGameObject {

    private List<Coordinate> coordinates = new ArrayList<>();
}
