package neverless.loader;

import neverless.domain.repository.MapObjectsRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MapLoaderTest {

    @Mock
    private MapObjectsRepository repository;
    @InjectMocks
    private MapLoader mapLoader;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadNpc() {
        mapLoader.createLandscape();
    }
}
