package neverless.service.util;

import neverless.domain.event.AbstractEvent;
import neverless.dto.event.EventsScreenDataDto;
import neverless.context.EventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventContext eventContext;

    /** Renders and returns DTO with happened events. */
    public EventsScreenDataDto getEventScreenData() {
        EventsScreenDataDto eventsScreenDataDto = new EventsScreenDataDto();
        List<AbstractEvent> events = eventContext.getEvents();
        eventsScreenDataDto.setEvents(events);
        return eventsScreenDataDto;
    }
}