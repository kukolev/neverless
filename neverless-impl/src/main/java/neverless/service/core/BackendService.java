package neverless.service.core;

import neverless.context.EventContext;
import neverless.context.RequestContext;
import neverless.Direction;
import neverless.dto.GameStateDto;
import neverless.service.ai.AiService;
import neverless.service.screendata.DialogService;
import neverless.service.screendata.EventService;
import neverless.service.screendata.GameService;
import neverless.service.screendata.InventoryService;
import neverless.service.screendata.NewGameService;
import neverless.service.screendata.PlayerService;
import neverless.service.screendata.QuestService;
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
    private NewGameService newGameService;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private GameService gameService;

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

    // todo: DRY
    public GameStateDto cmdStartNewGame() {
        newGameService.startNewGame();
        return getState();
    }

    public GameStateDto cmdWait() {
        long t = System.nanoTime();
        eventContext.clearEvents();
        requestContext.initQuestStates();
        aiService.handleEvents();
        questService.generateQuestEvents();
        System.out.println("cmdWait = " + (System.nanoTime() - t));
        return getState();
    }

    public GameStateDto cmdMoveDown() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.DOWN);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }

    public GameStateDto cmdMoveDownLeft() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.DOWN_LEFT);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }

    public GameStateDto cmdMoveDownRight() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.DOWN_RIGHT);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }


    public GameStateDto cmdMoveUp() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.UP);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }

    public GameStateDto cmdMoveUpLeft() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.UP_LEFT);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }

    public GameStateDto cmdMoveUpRight() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.UP_RIGHT);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }


    public GameStateDto cmdMoveLeft() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.LEFT);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }

    public GameStateDto cmdMoveRight() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.RIGHT);
        aiService.handleEvents();
        questService.generateQuestEvents();
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

    public GameStateDto cmdFightingAttack(String enemyId) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.attack(enemyId);
        aiService.handleEvents();
        questService.generateQuestEvents();
        return getState();
    }
}