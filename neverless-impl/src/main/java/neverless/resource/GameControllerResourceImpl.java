package neverless.resource;

import lombok.AllArgsConstructor;
import neverless.dto.screendata.player.GameStateDto;
import neverless.service.core.CommandRouterService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@AllArgsConstructor
@Transactional
public class GameControllerResourceImpl implements GameControllerResource {

    private CommandRouterService commandRouterService;

    @Override
    public GameStateDto getState() {
        return commandRouterService.getState();
    }

    @Override
    public GameStateDto cmdStartNewGame() {
        commandRouterService.cmdStartNewGame();
        return commandRouterService.getState();
    }

    @Override
    public GameStateDto cmdWait() {
        commandRouterService.cmdWait();
        return commandRouterService.getState();
    }

    @Override
    public GameStateDto cmdMoveDown() {
        commandRouterService.cmdMoveDown();
        return commandRouterService.getState();
    }

    @Override
    public GameStateDto cmdMoveDownLeft() {
        commandRouterService.cmdMoveDownLeft();
        return commandRouterService.getState();
    }

    @Override
    public GameStateDto cmdMoveDownRight() {
        commandRouterService.cmdMoveDownRight();
        return commandRouterService.getState();
    }

    @Override
    public GameStateDto cmdMoveUp() {
        commandRouterService.cmdMoveUp();
        return commandRouterService.getState();
    }

    @Override
    public GameStateDto cmdMoveUpLeft() {
        commandRouterService.cmdMoveUpLeft();
        return commandRouterService.getState();
    }

    @Override
    public GameStateDto cmdMoveUpRight() {
        commandRouterService.cmdMoveUpRight();
        return commandRouterService.getState();
    }

    @Override
    public GameStateDto cmdMoveLeft() {
        commandRouterService.cmdMoveLeft();
        return commandRouterService.getState();
    }

    @Override
    public GameStateDto cmdMoveRight() {
        commandRouterService.cmdMoveRight();
        return commandRouterService.getState();
    }

    @PostMapping("/dialog_start/npcX/{npcX}/npcY/{npcY}")
    public GameStateDto cmdDialogStart(@PathVariable Integer npcX,
                                       @PathVariable Integer npcY) {
        commandRouterService.cmdDialogStart(npcX, npcY);
        return commandRouterService.getState();
    }

    @Override
    @PostMapping("dialog_select_phrase/phraseNumber/{phraseNumber}")
    public GameStateDto cmdDialogSelectPhrase(@PathVariable Integer phraseNumber) {
        commandRouterService.cmdDialogSelectPhrase(phraseNumber);
        return commandRouterService.getState();
    }

    @Override
    @PostMapping("/inventory_clear_right_hand")
    public GameStateDto cmdInventoryClearRightHand() {
        commandRouterService.cmdInventoryClearRightHand();
        return commandRouterService.getState();
    }

    @Override
    @PostMapping("/inventory_clear_left_hand")
    public GameStateDto cmdInventoryClearLeftHand() {
        commandRouterService.cmdInventoryClearLeftHand();
        return commandRouterService.getState();
    }

    @Override
    @PostMapping("/inventory_equip_right_hand/itemId/{itemId}")
    public GameStateDto cmdInventoryEquipRightHand(@PathVariable Integer itemId) {
        commandRouterService.cmdInventoryEquipRightHand(itemId);
        return commandRouterService.getState();
    }

    @Override
    @PostMapping("/inventory_equip_left_hand/itemId/{itemId}")
    public GameStateDto cmdInventoryEquipLeftHand(@PathVariable Integer itemId) {
        commandRouterService.cmdInventoryEquipLeftHand(itemId);
        return commandRouterService.getState();
    }

    @Override
    @PostMapping("/fighting_attack/enemyId/{enemyId}")
    public GameStateDto cmdFightingAttack(@PathVariable String enemyId) {
        commandRouterService.cmdFightingAttack(enemyId);
        return commandRouterService.getState();
    }
}