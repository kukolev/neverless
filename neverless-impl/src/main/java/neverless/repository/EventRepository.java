package neverless.repository;

import neverless.domain.event.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static neverless.Constants.CURRENT_SESSION_ID;

@Repository
public class EventRepository {

    private Map<String, List<Event>> map = new ConcurrentHashMap<>();

    public void put(Event event) {
        List<Event> eventList = map.get(getSessionID());
        if (eventList == null) {
            eventList = new ArrayList<>();
            map.put(getSessionID(), eventList);
        }
        eventList.add(event);
    }

    public List<Event> get() {
        List<Event> eventList = map.get(getSessionID());
        if (eventList == null) {
            return new ArrayList<>();
        }
        return eventList;
    }

    private String getSessionID() {
        return CURRENT_SESSION_ID;
    }
}
