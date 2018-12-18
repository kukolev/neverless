package neverless.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.mapobject.Player;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Accessors(chain = true)
public class Game {

    @Id
    private String id;

    @OneToOne
    private Player player;

    @OneToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Location> locations = new ArrayList<>();

    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
    @Column
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Map<String, String> params = new HashMap<>();
}
