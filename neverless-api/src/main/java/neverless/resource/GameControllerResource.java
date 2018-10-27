package neverless.resource;

import neverless.dto.ResponseDto;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commands")
public interface GameControllerResource {

    @GetMapping("/")
    ResponseDto getState();

    @PostMapping("/start_new_game")
    ResponseDto cmdStartNewGame();

    @PostMapping("/map_go_down")
    ResponseDto cmdMoveDown();

    @PostMapping("/map_go_up")
    ResponseDto cmdMoveUp();

    @PostMapping("/map_go_left")
    ResponseDto cmdMoveLeft();

    @PostMapping("/map_go_right")
    ResponseDto cmdMoveRight();

    @PostMapping("/dialog_start/npcX/{npcX}/npcY/{npcY}")
    ResponseDto cmdDialogStart(@PathVariable Integer npcX,
                               @PathVariable Integer npcY);

    @PostMapping("/dialog_select_phrase/phraseNumber/{phraseNumber}")
    ResponseDto cmdDialogSelectPhrase(@PathVariable Integer phraseId);

    @PostMapping("/inventory_clear_right_hand")
    ResponseDto cmdInventoryClearRightHand();

    @PostMapping("/inventory_clear_left_hand")
    ResponseDto cmdInventoryClearLeftHand();

    @PostMapping("/inventory_equip_right_hand/weaponId/{weaponId}")
    ResponseDto cmdInventoryEquipRightHand(@PathVariable Integer weaponId);

    @PostMapping("/inventory_equip_left_hand/weaponId/{weaponId}")
    ResponseDto cmdInventoryEquipLeftHand(@PathVariable Integer weaponId);
}