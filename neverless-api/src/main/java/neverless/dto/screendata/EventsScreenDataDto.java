package neverless.dto.screendata;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class EventsScreenDataDto {

    private List<EventDto> events = new ArrayList<>();
}


