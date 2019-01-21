package neverless.service.core;

import neverless.command.AbstractCommand;
import neverless.command.player.WaitCommand;
import neverless.context.EventContext;
import neverless.context.RequestContext;
import neverless.domain.entity.mapobject.enemy.AbstractEnemy;
import neverless.dto.GameStateDto;
import neverless.service.ai.AiService;
import neverless.service.util.DialogService;
import neverless.service.util.EventService;
import neverless.service.util.GameService;
import neverless.service.util.InventoryService;
import neverless.service.util.NewGameService;
import neverless.service.behavior.PlayerBehaviorService;
import neverless.service.util.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackendService {

    @Autowired
    private DialogService dialogService;
    @Autowired
    private AiService aiService;
    @Autowired
    private QuestService questService;
    @Autowired
    private EventService eventService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private PlayerBehaviorService playerService;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private GameService gameService;
    @Autowired
    private CommandHandler commandHandler;

    // todo: DRY
    private GameStateDto getState() {
        long t = System.nanoTime();
        GameStateDto dto = new GameStateDto()
                .setGame(gameService.getGame())
                .setDialogScreenDataDto(dialogService.getScreenData())
                .setQuestScreenDataDto(questService.getScreenData())
                .setEventsScreenDataDto(eventService.getEventScreenData())
                .setTurnNumber(requestContext.incTurnNumber());
        System.out.println("getState = " + (System.nanoTime() - t));
        return dto;
    }

    public GameStateDto resolveCommand(AbstractCommand command) {
        long t = System.nanoTime();
        eventContext.clearEvents();
        requestContext.initQuestStates();
        commandHandler.processCommand(command);
        aiService.handleEvents();
        questService.generateQuestEvents();
        System.out.println("resolveCommand = " + (System.nanoTime() - t));

        return getState();
    }

    public GameStateDto cmdDialogStart(Integer npcX, Integer npcY) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        dialogService.dialogStart(npcX, npcY);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }

    public GameStateDto cmdDialogSelectPhrase(Integer phraseNumber) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        dialogService.selectPhrase(phraseNumber);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }

    public GameStateDto cmdInventoryClearRightHand() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        inventoryService.clearRightHand();
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }

    public GameStateDto cmdInventoryClearLeftHand() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        inventoryService.clearLeftHand();
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }

    public GameStateDto cmdInventoryEquipRightHand(Integer itemId) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        inventoryService.equipRightHand(itemId);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }

    public GameStateDto cmdInventoryEquipLeftHand(Integer itemId) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        inventoryService.equipLeftHand(itemId);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }

    public GameStateDto cmdFightingAttack(AbstractEnemy enemy) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.attack(enemy);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }
}