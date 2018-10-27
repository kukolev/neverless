package neverless.resource;

import lombok.AllArgsConstructor;
import neverless.dto.ResponseDto;
import neverless.service.core.CommandRouterService;
import neverless.service.screendata.LocalMapService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GameControllerResourceImpl implements GameControllerResource {

    private CommandRouterService commandRouterService;
    private LocalMapService localMapService;

    @Override
    public ResponseDto getState() {
        return commandRouterService.getState();
    }

    @Override
    public ResponseDto cmdStartNewGame() {
        commandRouterService.cmdStartNewGame();
        return commandRouterService.getState();
    }

    @Override
    public ResponseDto cmdMoveDown() {
        commandRouterService.cmdMoveDown();
        return commandRouterService.getState();
    }

    @Override
    public ResponseDto cmdMoveUp() {
        commandRouterService.cmdMoveUp();
        return commandRouterService.getState();
    }

    @Override
    public ResponseDto cmdMoveLeft() {
        commandRouterService.cmdMoveLeft();
        return commandRouterService.getState();
    }

    @Override
    public ResponseDto cmdMoveRight() {
        commandRouterService.cmdMoveRight();
        return commandRouterService.getState();
    }

    @PostMapping("/dialog_start/npcX/{npcX}/npcY/{npcY}")
    public ResponseDto cmdDialogStart(@PathVariable Integer npcX,
                                      @PathVariable Integer npcY) {
        commandRouterService.cmdDialogStart(npcX, npcY);
        return commandRouterService.getState();
    }

    @Override
    @PostMapping("dialog_select_phrase/phraseNumber/{phraseNumber}")
    public ResponseDto cmdDialogSelectPhrase(@PathVariable Integer phraseNumber) {
        commandRouterService.cmdDialogSelectPhrase(phraseNumber);
        return commandRouterService.getState();
    }

    @Override
    @PostMapping("/inventory_clear_right_hand")
    public ResponseDto cmdInventoryClearRightHand() {
        commandRouterService.cmdInventoryClearRightHand();
        return commandRouterService.getState();
    }

    @Override
    @PostMapping("/inventory_clear_left_hand")
    public ResponseDto cmdInventoryClearLeftHand() {
        commandRouterService.cmdInventoryClearLeftHand();
        return commandRouterService.getState();
    }

    @Override
    @PostMapping("/inventory_equip_right_hand/weaponId/{weaponId}")
    public ResponseDto cmdInventoryEquipRightHand(@PathVariable Integer weaponId) {
        commandRouterService.cmdInventoryEquipRightHand(weaponId);
        return commandRouterService.getState();
    }

    @Override
    @PostMapping("/inventory_equip_left_hand/weaponId/{weaponId}")
    public ResponseDto cmdInventoryEquipLeftHand(@PathVariable Integer weaponId) {
        commandRouterService.cmdInventoryEquipLeftHand(weaponId);
        return commandRouterService.getState();
    }
}