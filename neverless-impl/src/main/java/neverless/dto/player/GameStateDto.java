package neverless.dto.player;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.DialogScreenDataDto;
import neverless.dto.enemy.EnemyScreenDataDto;
import neverless.dto.event.EventsScreenDataDto;
import neverless.dto.LocalMapScreenDataDto;
import neverless.dto.inventory.InventoryScreenDataDto;
import neverless.dto.quest.QuestScreenDataDto;

@Data
@Accessors(chain = true)
public class GameStateDto {
    private int turnNumber;
    private LocalMapScreenDataDto localMapScreenData;
    private DialogScreenDataDto dialogScreenDataDto;
    private EventsScreenDataDto eventsScreenDataDto;
    private QuestScreenDataDto questScreenDataDto;
    private InventoryScreenDataDto inventoryScreenDataDto;
    private PlayerScreenDataDto playerScreenDataDto;
    private EnemyScreenDataDto enemyScreenDataDto;
}
