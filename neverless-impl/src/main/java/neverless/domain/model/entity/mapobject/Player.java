package neverless.domain.model.entity.mapobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.PlatformShape;
import neverless.domain.model.entity.mapobject.loot.AbstractLootFactory;
import neverless.domain.model.entity.mapobject.loot.LootContainer;
import neverless.game.Signatures;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Player extends AbstractLiveObject {

    @Override
    public String getSignature() {
        return Signatures.IMG_PLAYER;
    }

    @Override
    public PlatformShape getPlatformShape() {
        return PlatformShape.ELLIPSE;
    }

    @Override
    public boolean isHighlightable() {
        return false;
    }

    @Override
    protected AbstractLootFactory getLootFactory() {
        return new AbstractLootFactory() {
            @Override
            public LootContainer createLoot() {
                return new LootContainer();
            }
        };
    }
}