package neverless.resource;

import neverless.dto.screendata.player.GameStateDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands")
public interface GameControllerResource {

    @GetMapping("/")
    GameStateDto getState();

    @PostMapping("/start_new_game")
    GameStateDto cmdStartNewGame();

    @PostMapping("/wait")
    GameStateDto cmdWait();


    @PostMapping("/map_go_down")
    GameStateDto cmdMoveDown();

    @PostMapping("/map_go_up")
    GameStateDto cmdMoveUp();

    @PostMapping("/map_go_left")
    GameStateDto cmdMoveLeft();

    @PostMapping("/map_go_right")
    GameStateDto cmdMoveRight();

    @PostMapping("/dialog_start/npcX/{npcX}/npcY/{npcY}")
    GameStateDto cmdDialogStart(@PathVariable Integer npcX,
                                @PathVariable Integer npcY);

    @PostMapping("/dialog_select_phrase/phraseNumber/{phraseNumber}")
    GameStateDto cmdDialogSelectPhrase(@PathVariable Integer phraseId);

    @PostMapping("/inventory_clear_right_hand")
    GameStateDto cmdInventoryClearRightHand();

    @PostMapping("/inventory_clear_left_hand")
    GameStateDto cmdInventoryClearLeftHand();

    @PostMapping("/inventory_equip_right_hand/itemId/{itemId}")
    GameStateDto cmdInventoryEquipRightHand(@PathVariable Integer itemId);

    @PostMapping("/inventory_equip_left_hand/itemId/{itemId}")
    GameStateDto cmdInventoryEquipLeftHand(@PathVariable Integer itemId);

    @PostMapping("/fighting_attack/enemyId/{enemyId}")
    GameStateDto cmdFightingAttack(@PathVariable String enemyId);
}