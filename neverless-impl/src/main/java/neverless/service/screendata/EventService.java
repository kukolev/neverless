package neverless.service.screendata;

import neverless.mapper.EventMapper;
import neverless.domain.event.AbstractEvent;
import neverless.dto.screendata.event.EventsScreenDataDto;
import neverless.service.core.EventContext;
import neverless.service.core.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService extends AbstractService {

    @Autowired
    private EventContext eventContext;
    @Autowired
    private EventMapper mapper;

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