package neverless.domain.event;

import neverless.dto.screendata.event.EventType;

public class InventoryLeftHandEquipEvent extends AbstractEvent {

    @Override
    public EventType getEventType() {
        return EventType.LEFT_HAND_EQUIP_EVENT;
    }
}
