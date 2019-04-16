package neverless.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.domain.entity.mapobject.AbstractArea;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.npc.AbstractNpc;
import neverless.domain.entity.mapobject.portal.LocationPortal;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Location extends AbstractGameObject{

    private String title;
    private String signature;
    private List<AbstractMapObject> objects = new CopyOnWriteArrayList<>();
    private List<AbstractArea> areas = new CopyOnWriteArrayList<>();

    public List<AbstractRespawnPoint> getRespawnPoints() {
        return objects.stream()
                .filter(o -> o instanceof AbstractRespawnPoint)
                .map(o -> (AbstractRespawnPoint) o)
                .collect(Collectors.toList());
    }

    public List<AbstractNpc> getNpcs() {
        return objects.stream()
                .filter(o -> o instanceof AbstractNpc)
                .map(o -> (AbstractNpc) o)
                .collect(Collectors.toList());
    }

    public List<LocationPortal> getPortals() {
        return areas.stream()
                .filter(o -> o instanceof LocationPortal)
                .map(o -> (LocationPortal) o)
                .collect(Collectors.toList());
    }
}
