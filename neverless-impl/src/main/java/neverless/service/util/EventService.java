package neverless.service.util;

import neverless.dto.event.EventsScreenDataDto;
import neverless.context.EventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventContext eventContext;

    /** Renders and returns DTO with happened events. */
    public EventsScreenDataDto getEventScreenData() {
        EventsScreenDataDto eventsScreenDataDto = new EventsScreenDataDto();
        eventsScreenDataDto.setEvents(eventContext.getEvents());
        return eventsScreenDataDto;
    }
}