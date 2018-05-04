package neverless.service.rendering;


import neverless.domain.game.mapobject.tree.FirTree;
import neverless.dto.screendata.LocalMapScreenDataDto;
import neverless.service.RenderService;
import neverless.loader.MapLoader;
import neverless.domain.game.mapobject.AbstractMapObject;
import neverless.domain.game.mapobject.Player;
import neverless.domain.repository.MapObjectsRepository;
import neverless.domain.repository.PlayerRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


public class RenderServiceTest {

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private MapObjectsRepository mapObjRepository;
    @InjectMocks
    private RenderService renderService;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        when(playerRepository.get(eq("Vova"))).thenReturn(createTestPlayer(100, 100));
        when(mapObjRepository.findAll()).thenReturn(createTestMapObjects());
        LocalMapScreenDataDto localMapScreenData = renderService.calcLocalMap();
        System.out.println(localMapScreenData);
    }

    @Test
    public void testRenderInitialMap() throws Exception {
        PlayerRepository playerRepository = new PlayerRepository();
        playerRepository.save(createTestPlayer(60, 50));
        MapObjectsRepository mapObjRepository = new MapObjectsRepository();
        MapLoader dataLoader = new MapLoader(mapObjRepository);
        dataLoader.createLandscape();
        RenderService renderService = new RenderService(playerRepository, mapObjRepository);
        LocalMapScreenDataDto localMapScreenData = renderService.calcLocalMap();
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