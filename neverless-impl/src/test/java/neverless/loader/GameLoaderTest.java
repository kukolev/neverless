package neverless.loader;

import neverless.game.GameLoader;
import neverless.repository.MapObjectsRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GameLoaderTest {

    @Mock
    private MapObjectsRepository repository;
    @InjectMocks
    private GameLoader gameLoader;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadNpc() {
        gameLoader.createLandscape();
    }
}
