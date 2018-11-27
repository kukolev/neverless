package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Resouces;
import neverless.domain.entity.inventory.Inventory;

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

    @Column
    private Integer turnNumber = 0;

    @Override
    public String getSignature() {
        return Resouces.IMG_PLAYER;
    }

    public int incAndGetTurnNumber() {
        return ++turnNumber;
    }

    public void decreaseHitPoints(int damage) {
        hitPoints -= damage;
    }
}