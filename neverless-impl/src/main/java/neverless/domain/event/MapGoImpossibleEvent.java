package neverless.domain.event;

public class MapGoImpossibleEvent extends AbstractEvent {

    @Override
    public EventType getType() {
        return EventType.MAP_GO_IMPASSABLE_EVENT;
    }
}
