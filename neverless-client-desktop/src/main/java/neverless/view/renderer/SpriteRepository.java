package neverless.view.renderer;

import javafx.scene.image.Image;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpriteRepository {

    private Map<String, Image> cache = new ConcurrentHashMap<>();

    public Image getImage(String key) {
        Image image = cache.get(key);
        if (image == null) {
            image = new Image("sprites/" + key);
            cache.put(key, image);
        }
        return image;
    }
}
