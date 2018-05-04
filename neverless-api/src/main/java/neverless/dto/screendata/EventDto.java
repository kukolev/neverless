package neverless.dto.screendata;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EventDto {

    private String number;
    private String type;
}
