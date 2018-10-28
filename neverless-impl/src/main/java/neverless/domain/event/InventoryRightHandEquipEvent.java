package neverless.domain.event;

import neverless.dto.screendata.event.EventType;

public class InventoryRightHandEquipEvent extends AbstractEvent {

    @Override
    public EventType getEventType() {
        return EventType.RIGHT_HAND_EQUIP_EVENT;
    }
}