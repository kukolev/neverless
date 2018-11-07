package neverless.domain.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import neverless.domain.inventory.Inventory;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
public class Player extends AbstractMapObject {

    @Transient
    private Dialog dialog;

    @Transient
    private NpcPhrase npcPhrase;

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