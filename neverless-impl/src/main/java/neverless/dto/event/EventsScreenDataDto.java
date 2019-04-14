package neverless.dto.event;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.event.AbstractEvent;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class EventsScreenDataDto {

    private List<AbstractEvent> events = new ArrayList<>();
}