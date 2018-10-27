package neverless.dto.screendata.event;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class EventsScreenDataDto {

    private List<Map<String, Object>> events = new ArrayList<>();
}


