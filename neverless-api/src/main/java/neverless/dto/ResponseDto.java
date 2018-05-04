package neverless.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.screendata.DialogScreenDataDto;
import neverless.dto.screendata.EventsScreenDataDto;
import neverless.dto.screendata.LocalMapScreenDataDto;

@Data
@Accessors(chain = true)
public class ResponseDto {
    private int turnNumber;
    private LocalMapScreenDataDto localMapScreenData;
    private DialogScreenDataDto dialogScreenDataDto;
    private EventsScreenDataDto eventsScreenDataDto;
}
