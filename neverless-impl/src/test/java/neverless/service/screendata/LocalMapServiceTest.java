package neverless.service.screendata;


import neverless.domain.mapobject.tree.FirTree;
import neverless.dto.screendata.LocalMapScreenDataDto;
import neverless.game.GameLoader;
import neverless.service.screendata.LocalMapService;
import neverless.domain.mapobject.AbstractMapObject;
import neverless.domain.mapobject.Player;
import neverless.repository.MapObjectsRepository;
import neverless.repository.PlayerRepository;
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
    @InjectMocks
    private LocalMapService localMapService;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        when(playerRepository.get(eq("Vova"))).thenReturn(createTestPlayer(100, 100));
        when(mapObjRepository.findAll()).thenReturn(createTestMapObjects());
        LocalMapScreenDataDto localMapScreenData = localMapService.getScreenData();
        System.out.println(localMapScreenData);
    }

    @Test
    public void testRenderInitialMap() throws Exception {
        PlayerRepository playerRepository = new PlayerRepository();
        playerRepository.save(createTestPlayer(60, 50));
        MapObjectsRepository mapObjRepository = new MapObjectsRepository();
        GameLoader dataLoader = new GameLoader(mapObjRepository);
        dataLoader.createLandscape();
        LocalMapService localMapService = new LocalMapService(playerRepository, mapObjRepository);
        LocalMapScreenDataDto localMapScreenData = localMapService.getScreenData();
        System.out.println(localMapScreenData);
    }

    private List<AbstractMapObject> createTestMapObjects() {
        List<AbstractMapObject> result = new ArrayList<>();
        FirTree tree = new FirTree();
        tree.setX(96);
        tree.setY(96);
        tree.setHeight(2);
        tree.setWidth(2);

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