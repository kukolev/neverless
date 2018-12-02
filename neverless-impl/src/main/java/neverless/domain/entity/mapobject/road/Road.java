package neverless.domain.entity.mapobject.road;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Resources;

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
        return Resources.IMG_ROAD;
    }
}
