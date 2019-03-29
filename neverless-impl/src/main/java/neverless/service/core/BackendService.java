package neverless.service.core;

import neverless.command.Command;
import neverless.context.EventContext;
import neverless.context.RequestContext;
import neverless.dto.GameStateDto;
import neverless.repository.cache.GameCache;
import neverless.service.ai.AiService;
import neverless.service.util.DialogService;
import neverless.service.util.EventService;
import neverless.service.util.InventoryService;
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
    private EventContext eventContext;
    @Autowired
    private GameCache gameCache;
    @Autowired
    private CommandRouter commandRouter;

    // todo: DRY
    private GameStateDto getState() {
        GameStateDto dto = new GameStateDto()
                .setGame(gameCache.getGame())
                .setDialogScreenDataDto(dialogService.getScreenData())
                .setQuestScreenDataDto(questService.getScreenData())
                .setEventsScreenDataDto(eventService.getEventScreenData())
                .setTurnNumber(requestContext.incTurnNumber());
        return dto;
    }

    public GameStateDto resolveCommand(Command command) {
        eventContext.clearEvents();
        requestContext.initQuestStates();
        commandRouter.processCommand(command);
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
}