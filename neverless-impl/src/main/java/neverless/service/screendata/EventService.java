package neverless.service.screendata;

import neverless.domain.event.Event;
import neverless.repository.EventRepository;
import neverless.dto.screendata.event.EventDto;
import neverless.dto.screendata.event.EventsScreenDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public void add(Event event) {
        repository.put(event);
    }

    public List<Event> get() {
        return repository.get();
    }

    public void clear() {
        List<Event> events = get();
        events.clear();
    }

    public EventsScreenDataDto getScreenData() {
        EventsScreenDataDto eventsScreenDataDto = new EventsScreenDataDto();
        List<Event> events = get();
        events.forEach(evt -> {
            EventDto dto = new EventDto();
            dto.setType(evt.getType().name());
            eventsScreenDataDto.getEvents().add(dto);
        });
        return eventsScreenDataDto;
    }
}
