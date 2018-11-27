package neverless.domain.entity.mapobject.road;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Resouces;

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
        return Resouces.IMG_ROAD;
    }
}
