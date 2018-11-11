package neverless.service.screendata;

import neverless.mapper.EventMapper;
import neverless.domain.event.AbstractEvent;
import neverless.dto.screendata.event.EventsScreenDataDto;
import neverless.context.EventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventContext eventContext;
    @Autowired
    private EventMapper mapper;

    /** Renders and returns DTO with happened events. */
    public EventsScreenDataDto getEventScreenData() {
        EventsScreenDataDto eventsScreenDataDto = new EventsScreenDataDto();
        List<AbstractEvent> events = eventContext.getEvents();
        List<Map<String, Object>> eventDtos = events.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
        eventsScreenDataDto.setEvents(eventDtos);
        return eventsScreenDataDto;
    }
}