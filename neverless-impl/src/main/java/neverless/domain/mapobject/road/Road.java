package neverless.domain.mapobject.road;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class Road extends AbstractRoad {

    public Road() {
        setZOrder(0);
    }

    @Override
    public String getSignature() {
        return "ROAD_";
    }
}
