package neverless.service.core;

import neverless.context.EventContext;
import neverless.context.RequestContext;
import neverless.Direction;
import neverless.dto.player.GameStateDto;
import neverless.service.ai.AiService;
import neverless.service.screendata.DialogService;
import neverless.service.screendata.EnemyService;
import neverless.service.screendata.EventService;
import neverless.service.screendata.InventoryService;
import neverless.service.screendata.LocalMapService;
import neverless.service.screendata.NewGameService;
import neverless.service.screendata.PlayerService;
import neverless.service.screendata.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandRouterService {

    @Autowired
    private LocalMapService localMapService;
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
    private EnemyService enemyService;
    @Autowired
    private EventContext eventContext;

    public GameStateDto getState() {
        long t = System.nanoTime();
        GameStateDto dto = new GameStateDto()
                .setLocalMapScreenData(localMapService.getScreenData())
                .setDialogScreenDataDto(dialogService.getScreenData())
                .setQuestScreenDataDto(questService.getScreenData())
                .setEventsScreenDataDto(eventService.getEventScreenData())
                .setInventoryScreenDataDto(inventoryService.getScreenData())
                .setPlayerScreenDataDto(playerService.getScreenData())
                .setEnemyScreenDataDto(enemyService.getScreenData())
                .setTurnNumber(requestContext.incTurnNumber());
        System.out.println("getState = " + (System.nanoTime() - t));
        return dto;
    }

    // todo: DRY

    public void cmdStartNewGame() {
        newGameService.startNewGame();
    }

    public void cmdWait() {
        long t = System.nanoTime();
        eventContext.clearEvents();
        requestContext.initQuestStates();
        aiService.handleEvents();
        questService.generateQuestEvents();
        System.out.println("cmdWait = " + (System.nanoTime() - t));
    }

    public void cmdMoveDown() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.DOWN);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdMoveDownLeft() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.DOWN_LEFT);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdMoveDownRight() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.DOWN_RIGHT);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }


    public void cmdMoveUp() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.UP);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdMoveUpLeft() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.UP_LEFT);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdMoveUpRight() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.UP_RIGHT);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }


    public void cmdMoveLeft() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.LEFT);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdMoveRight() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.goOnDirection(Direction.RIGHT);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdDialogStart(Integer npcX, Integer npcY) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        dialogService.dialogStart(npcX, npcY);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdDialogSelectPhrase(Integer phraseNumber) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        dialogService.selectPhrase(phraseNumber);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdInventoryClearRightHand() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        inventoryService.clearRightHand();
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdInventoryClearLeftHand() {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        inventoryService.clearLeftHand();
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdInventoryEquipRightHand(Integer itemId) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        inventoryService.equipRightHand(itemId);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdInventoryEquipLeftHand(Integer itemId) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        inventoryService.equipLeftHand(itemId);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdFightingAttack(String enemyId) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        playerService.attack(enemyId);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }
}