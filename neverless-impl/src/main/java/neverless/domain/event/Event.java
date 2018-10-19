package neverless.domain.event;


import lombok.Data;
import lombok.Getter;

import java.util.HashMap;

@Data
public class Event {

    @Getter
    private EventType type;

    private HashMap<String, Object> params = new HashMap<>();

    public Event(EventType type) {
        this.type = type;
    }

    public void addParam(String param, Object value) {
        params.put(param, value);
    }
}
