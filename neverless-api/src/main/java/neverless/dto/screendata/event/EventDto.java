package neverless.dto.screendata.event;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EventDto {

    private String type;
}
