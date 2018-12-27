package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Resources;
import neverless.domain.Location;
import neverless.domain.entity.inventory.Inventory;
import neverless.MapObjectMetaType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Accessors(chain = true)
@Entity
public class Player extends AbstractMapObject {

    @Column
    private Integer hitPoints = 100;

    @OneToOne
    private Inventory inventory;

    @OneToOne
    private Location location;

    @Override
    public String getSignature() {
        return Resources.IMG_PLAYER;
    }

    public void decreaseHitPoints(int damage) {
        hitPoints -= damage;
    }

    @Override
    public MapObjectMetaType getMetaType() {
        return MapObjectMetaType.PLAYER;
    }
}