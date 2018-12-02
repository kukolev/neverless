package neverless.dto.screendata.player;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.screendata.DialogScreenDataDto;
import neverless.dto.screendata.enemy.EnemyScreenDataDto;
import neverless.dto.screendata.event.EventsScreenDataDto;
import neverless.dto.screendata.LocalMapScreenDataDto;
import neverless.dto.screendata.inventory.InventoryScreenDataDto;
import neverless.dto.screendata.quest.QuestScreenDataDto;

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
