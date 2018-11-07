package neverless.domain.mapobject.monster;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.mapobject.AbstractMapObject;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractEnemy extends AbstractMapObject{

    @Column
    private Integer bornX;

    @Column
    private Integer bornY;

    @Column
    private Integer areaX;

    @Column
    private Integer areaY;

    @Column
    private Integer agrRange = 7; // todo: should be in constants
}
