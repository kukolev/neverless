package neverless.domain.game.mapobject.road;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class Road extends AbstractRoad {

    public Road() {
        setHeight(1);
        setWidth(1);
        setZOrder(0);
        setSignature("ROAD_");
    }
}
