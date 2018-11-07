package neverless.domain.mapobject.road;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public final class Road extends AbstractRoad {

    public Road() {
        setZOrder(0);
    }

    @Override
    public String getSignature() {
        return "ROAD_";
    }
}
