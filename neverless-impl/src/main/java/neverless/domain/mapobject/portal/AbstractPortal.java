package neverless.domain.mapobject.portal;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.mapobject.AbstractMapObject;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public class AbstractPortal extends AbstractMapObject {

    @Column
    private String destination;
    @Column
    private Integer destX;
    @Column
    private Integer destY;

    @Override
    public String getSignature() {
        return "PORTAL_";
    }

    @Override
    public boolean isPassable() {
        return true;
    }
}
