package neverless.dto.event;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.event.AbstractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Accessors(chain = true)
public class EventsScreenDataDto {

    private Map<String, List<AbstractEvent>> events = new ConcurrentHashMap<>();
}