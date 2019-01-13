package neverless.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.Game;
import neverless.dto.event.EventsScreenDataDto;
import neverless.dto.quest.QuestScreenDataDto;

@Data
@Accessors(chain = true)
public class GameStateDto {
    private int turnNumber;
    private Game game;
    private DialogScreenDataDto dialogScreenDataDto;
    private EventsScreenDataDto eventsScreenDataDto;
    private QuestScreenDataDto questScreenDataDto;
}