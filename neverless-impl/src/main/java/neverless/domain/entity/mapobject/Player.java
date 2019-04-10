package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.PlatformShape;
import neverless.Signatures;
import neverless.domain.entity.Location;
import neverless.domain.entity.inventory.Inventory;
import neverless.MapObjectMetaType;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Player extends AbstractMapObject {

    private Integer hitPoints = 100;
    private Inventory inventory;
    private Location location;

    @Override
    public String getSignature() {
        return Signatures.IMG_PLAYER;
    }

    @Override
    public PlatformShape getPlatformShape() {
        return PlatformShape.ELLIPSE;
    }

    public void decreaseHitPoints(int damage) {
        hitPoints -= damage;
    }

    @Override
    public MapObjectMetaType getMetaType() {
        return MapObjectMetaType.PLAYER;
    }
}