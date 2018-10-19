package neverless.service.core;

import lombok.AllArgsConstructor;
import neverless.domain.*;
import neverless.dto.command.Direction;
import neverless.service.command.*;
import neverless.service.screendata.DialogService;
import neverless.service.screendata.EventService;
import neverless.service.screendata.LocalMapService;
import neverless.service.screendata.QuestService;
import neverless.service.ai.AiService;
import neverless.dto.ResponseDto;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommandRouterService {

    private LocalMapService localMapService;
    private DialogService dialogService;
    private AiService aiService;
    private QuestService questService;
    private EventService eventService;

    private MapGoCommand goDownCommand;
    private MapGoCommand goUpCommand;
    private MapGoCommand goLeftCommand;
    private MapGoCommand goRightCommand;

    private DialogStartCommand dialogStartCommand;
    private DialogSelectPhraseCommand dialogSelectPhraseCommand;

    private MenuStartNewGameCommand startNewGameCommand;

    private static final EmptyParams EMPTY_PARAMS = new EmptyParams();

    public ResponseDto getState() {
        return new ResponseDto()
                .setLocalMapScreenData(localMapService.getScreenData())
                .setDialogScreenDataDto(dialogService.getScreenData())
                .setQuestScreenDataDto(questService.getScreenData())
                .setEventsScreenDataDto(eventService.getEventScreenData());
    }

    private void execute(AbstractCommand command, AbstractCommandParams params) {
        command.execute(params);
        aiService.handleEvents(command.getEvents());
    }

    private void execute(AbstractCommand command) {
        execute(command, EMPTY_PARAMS);
    }

    public void cmdStartNewGame() {
        execute(startNewGameCommand);
    }

    public void cmdMoveDown() {
        MapGoParams params = new MapGoParams()
                .setDirection(Direction.DOWN);
        execute(goDownCommand, params);
    }

    public void cmdMoveUp() {
        MapGoParams params = new MapGoParams()
                .setDirection(Direction.UP);
        execute(goUpCommand, params);
    }

    public void cmdMoveLeft() {
        MapGoParams params = new MapGoParams()
                .setDirection(Direction.LEFT);
        execute(goLeftCommand, params);
    }

    public void cmdMoveRight() {
        MapGoParams params = new MapGoParams()
                .setDirection(Direction.RIGHT);
        execute(goRightCommand, params);
    }

    public void cmdDialogStart(Integer npcX, Integer npcY) {
        DialogStartParams params = new DialogStartParams()
                .setNpcX(npcX)
                .setNpcY(npcY);
        execute(dialogStartCommand, params);
    }

    public void cmdDialogSelectPhrase(Integer phraseNumber) {
        DialogSelectPhraseParams params = new DialogSelectPhraseParams()
                .setPhraseNumber(phraseNumber);
        execute(dialogSelectPhraseCommand, params);
    }
}