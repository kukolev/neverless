package neverless.domain.entity.mapobject.loot;

import lombok.Data;
import neverless.domain.entity.item.AbstractItem;
import neverless.domain.entity.mapobject.AbstractPhysicalObject;

import java.util.ArrayList;
import java.util.List;

import static neverless.Signatures.IMG_LOOT;

@Data
public class LootItem extends AbstractPhysicalObject {

    private List<AbstractItem> items = new ArrayList<>();

    @Override
    public String getSignature() {
        return IMG_LOOT;
    }
}