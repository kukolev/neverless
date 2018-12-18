package neverless.domain;

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

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AbstractMapObject> objects = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AbstractRespawnPoint> respawnPoints = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AbstractNpc> npcs = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AbstractPortal> portals = new ArrayList<>();
}
