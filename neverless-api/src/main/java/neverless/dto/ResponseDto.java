package neverless.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.screendata.DialogScreenDataDto;
import neverless.dto.screendata.event.EventsScreenDataDto;
import neverless.dto.screendata.LocalMapScreenDataDto;
import neverless.dto.screendata.quest.QuestScreenDataDto;

@Data
@Accessors(chain = true)
public class ResponseDto {
    private int turnNumber;
    private LocalMapScreenDataDto localMapScreenData;
    private DialogScreenDataDto dialogScreenDataDto;
    private EventsScreenDataDto eventsScreenDataDto;
    private QuestScreenDataDto questScreenDataDto;
}
