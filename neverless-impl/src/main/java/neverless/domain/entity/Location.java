package neverless.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.npc.AbstractNpc;
import neverless.domain.entity.mapobject.portal.AbstractPortal;
import neverless.domain.entity.mapobject.respawn.AbstractRespawnPoint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Accessors(chain = true)
public class Location {

    @Id
    @GeneratedValue(generator = "generic-uuid")
    @GenericGenerator(name = "generic-uuid", strategy = "uuid")
    private String id;

    @Column
    private String title;

    @Column
    private String signature;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "location")
    @Fetch(value = FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AbstractMapObject> objects = new ArrayList<>();

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

    public List<AbstractPortal> getPortals() {
        return objects.stream()
                .filter(o -> o instanceof AbstractPortal)
                .map(o -> (AbstractPortal) o)
                .collect(Collectors.toList());
    }
}
