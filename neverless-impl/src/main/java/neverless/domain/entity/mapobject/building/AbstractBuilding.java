package neverless.domain.entity.mapobject.building;


import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.mapobject.AbstractMapObject;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractBuilding extends AbstractMapObject {

}
