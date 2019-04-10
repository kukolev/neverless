package neverless.view.renderer;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import neverless.resource.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpriteRepository {

    private Map<String, Image> fileCache = new ConcurrentHashMap<>();
    private Map<Resource, Image> resourceCache = new ConcurrentHashMap<>();

    /**
     * Returns image for specified resource.
     *
     * @param res   resource with information about image location (file name and position in file)
     */
    public Image getImage(Resource res) {

        // Load file with image from cache
        // + update cache if needed.
        Image mainImg = fileCache.get(res.getFile());
        if (mainImg == null) {
            mainImg = new Image("/sprites/" + res.getFile());
            fileCache.put(res.getFile(), mainImg);
        }

        // Load image from resource cache
        // + update cache if needed
        Image image = resourceCache.get(res);
        if (image == null) {
            image = copyImage(mainImg, res.getLeft(), res.getTop(), res.getWidth(), res.getHeight());
            resourceCache.put(res, image);
        }
        return image;
    }

    /**
     * Returns subimage of the given image
     *
     * @param image     given image.
     * @param left      left margin of result image.
     * @param top       top margin of result image.
     * @param width     width of result image.
     * @param height    height of result image.
     */
    private static WritableImage copyImage(Image image, int left, int top, int width, int height) {
        PixelReader pixelReader=image.getPixelReader();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int i = left; i < left + width; i++){
            for (int j = top; j < top + height; j++){
                Color color = pixelReader.getColor(i, j);
                pixelWriter.setColor(i - left, j - top, color);
            }
        }
        return writableImage;
    }
}