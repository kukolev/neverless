package neverless.domain.model.entity.mapobject.loot;

import lombok.Data;
import neverless.domain.model.entity.item.AbstractItem;
import neverless.domain.model.entity.mapobject.AbstractPhysicalObject;

import java.util.ArrayList;
import java.util.List;

import static neverless.game.Signatures.IMG_LOOT;

@Data
public class LootContainer extends AbstractPhysicalObject {

    private List<AbstractItem> items = new ArrayList<>();

    @Override
    public String getSignature() {
        return IMG_LOOT;
    }
}