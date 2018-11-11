package neverless.domain.entity.mapobject.enemy;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.item.weapon.AbstractMeleeWeapon;
import neverless.domain.entity.mapobject.AbstractMapObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractEnemy extends AbstractMapObject {

    @Column
    private Integer healthPoints;

    @OneToMany
    private List<AbstractMeleeWeapon> weapons = new ArrayList<>();

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