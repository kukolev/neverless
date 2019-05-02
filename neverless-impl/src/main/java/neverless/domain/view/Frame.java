package neverless.domain.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Frame object contains all information for local map rendering.
 */
@Data
@Accessors(chain = true)
public class Frame {
    private Sprite background;
    private List<Sprite> sprites = new ArrayList<>();
    private List<String> log = new ArrayList<>();
    private DestinationMarkerEffect marker;
    private AreaHighlighted areaHighlighted;
    private ProfileWidget profileWidget = new ProfileWidget();

    /**
     * Container for object ids that should be high-lighted
     */
    private List<String> highLighted = new ArrayList<>();

    /**
     * Adds one particular id of object to list of objects that should be highlighted
     *
     * @param id    object id.
     * @param clean condition for list cleaning before adding ids.
     */
    public void addHighLighted(String id, boolean clean) {
        if (clean) {
            highLighted.clear();
        }
        highLighted.add(id);
    }

    /**
     * Adds log item to list of log items.
     *
     * @param logItem   log item.
     */
    public void addLog(String logItem) {
        log.add(logItem);
    }
}
