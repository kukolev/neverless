package neverless.service.core;

import neverless.context.RequestContext;
import neverless.domain.entity.mapobject.Player;
import neverless.dto.command.Direction;
import neverless.dto.screendata.player.GameStateDto;
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

import javax.transaction.Transactional;

@Service
@Transactional
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

    public GameStateDto getState() {
        long t = System.nanoTime();
        Player player = playerService.getPlayer();

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
        requestContext.initQuestStates();
        aiService.handleEvents();
        questService.generateQuestEvents();
        System.out.println("cmdWait = " + (System.nanoTime() - t));
    }

    public void cmdMoveDown() {
        requestContext.initQuestStates();
        localMapService.mapGo(Direction.DOWN);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdMoveUp() {
        requestContext.initQuestStates();
        localMapService.mapGo(Direction.UP);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdMoveLeft() {
        requestContext.initQuestStates();
        localMapService.mapGo(Direction.LEFT);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdMoveRight() {
        requestContext.initQuestStates();
        localMapService.mapGo(Direction.RIGHT);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdDialogStart(Integer npcX, Integer npcY) {
        requestContext.initQuestStates();
        dialogService.dialogStart(npcX, npcY);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdDialogSelectPhrase(Integer phraseNumber) {
        requestContext.initQuestStates();
        dialogService.selectPhrase(phraseNumber);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdInventoryClearRightHand() {
        requestContext.initQuestStates();
        inventoryService.clearRightHand();
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdInventoryClearLeftHand() {
        requestContext.initQuestStates();
        inventoryService.clearLeftHand();
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdInventoryEquipRightHand(Integer itemId) {
        requestContext.initQuestStates();
        inventoryService.equipRightHand(itemId);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdInventoryEquipLeftHand(Integer itemId) {
        requestContext.initQuestStates();
        inventoryService.equipLeftHand(itemId);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }

    public void cmdFightingAttack(String enemyId) {
        requestContext.initQuestStates();
        playerService.attack(enemyId);
        aiService.handleEvents();
        questService.generateQuestEvents();
    }
}