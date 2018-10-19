package neverless.service.screendata;

import neverless.domain.event.Event;
import neverless.dto.screendata.event.EventDto;
import neverless.dto.screendata.event.EventsScreenDataDto;
import neverless.service.core.EventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventContext eventContext;

    public EventsScreenDataDto getEventScreenData() {
        EventsScreenDataDto eventsScreenDataDto = new EventsScreenDataDto();
        List<Event> events = eventContext.getEvents();
        List<EventDto> eventDtos = events.stream()
                .map(this::mapEventToDto)
                .collect(Collectors.toList());
        eventsScreenDataDto.setEvents(eventDtos);
        return eventsScreenDataDto;
    }

    private EventDto mapEventToDto(Event event) {
        return new EventDto()
                .setType(event.getType().name());
    }
}
