package neverless.domain.mapobject.building;


import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.mapobject.AbstractMapObject;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractBuilding extends AbstractMapObject {

}
