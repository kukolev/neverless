package neverless.model;

import neverless.context.EventContext;
import neverless.context.GameContext;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.event.AbstractEvent;
import neverless.domain.event.MapGoEvent;
import neverless.model.domain.ViewContext;
import neverless.model.domain.DestinationMarkerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventHandler {

    @Autowired
    private GameContext gameContext;
    @Autowired
    private EventContext eventContext;

    /**
     * Handles events and updates ViewContext.
     *
     * @param viewContext   ViewContext instance.
     */
    public void handleEvents(ViewContext viewContext) {
        MapGoEvent event = findMapGoEvent();
        if (event != null) {
            viewContext.setMarker(new DestinationMarkerData()
                    .setX(event.getTargetX())
                    .setY(event.getTargetY()));
        } else {
            viewContext.setMarker(null);
        }
    }

    /**
     * Returns MapGoEvent if present in list of events.
     */
    private MapGoEvent findMapGoEvent() {
        Player player = gameContext.getPlayer();
        List<AbstractEvent> events = eventContext.getEvents();
        return events.stream()
                .filter(e -> e instanceof MapGoEvent)
                .map(e -> (MapGoEvent) e)
                .filter(e -> e.getId().equals(player.getUniqueName()))
                .findFirst()
                .orElse(null);
    }
}