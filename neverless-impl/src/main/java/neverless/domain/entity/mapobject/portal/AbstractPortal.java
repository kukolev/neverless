package neverless.domain.entity.mapobject.portal;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Resources;
import neverless.domain.Location;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.MapObjectMetaType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Accessors(chain = true)
@Entity
public class AbstractPortal extends AbstractMapObject {

    @OneToOne
    private Location destination;
    @Column
    private Integer destX;
    @Column
    private Integer destY;

    @Override
    public String getSignature() {
        return Resources.IMG_PORTAL;
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public MapObjectMetaType getMetaType() {
        return MapObjectMetaType.PORTAL;
    }
}
