package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import neverless.domain.entity.inventory.Inventory;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
public class Player extends AbstractMapObject {

    @OneToOne
    private Inventory inventory;

    @Column
    private Integer turnNumber = 0;

    @Override
    public String getSignature() {
        return "PLAYER_";
    }

    public int incAndGetTurnNumber() {
        return ++turnNumber;
    }
}