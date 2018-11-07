package neverless.service.screendata;


import neverless.domain.mapobject.tree.FirTree;
import neverless.dto.screendata.LocalMapScreenDataDto;
import neverless.game.GameLoader;
import neverless.domain.mapobject.AbstractMapObject;
import neverless.domain.mapobject.Player;
import neverless.repository.MapObjectsRepository;
import neverless.repository.PlayerRepository;
import neverless.repository.RespawnPointRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


public class LocalMapServiceTest {

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private MapObjectsRepository mapObjRepository;
    @Mock
    private RespawnPointRepository respawnPointRepository;
    @InjectMocks
    private LocalMapService localMapService;
    @InjectMocks
    private GameLoader dataLoader;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private List<AbstractMapObject> createTestMapObjects() {
        List<AbstractMapObject> result = new ArrayList<>();
        FirTree tree = new FirTree();
        tree.setX(96);
        tree.setY(96);

        result.add(tree);
        return result;
    }

    private Player createTestPlayer(int x, int y) {
        Player player = new Player();
        player.setX(x);
        player.setY(y);
        player.setUniqueName("Vova");
        return player;
    }
}