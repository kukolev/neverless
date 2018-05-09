package neverless.service.core;

import lombok.AllArgsConstructor;
import neverless.dto.command.CommandName;
import neverless.service.screendata.DialogService;
import neverless.service.screendata.EventService;
import neverless.service.screendata.LocalMapService;
import neverless.service.screendata.QuestService;
import neverless.service.ai.AiService;
import neverless.service.command.AbstractCommand;
import neverless.dto.command.CommandDto;
import neverless.dto.ResponseDto;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommandRouterService {

    private CommandMapper commandMapper;
    private LocalMapService localMapService;
    private DialogService dialogService;
    private EventService eventService;
    private AiService aiService;
    private QuestService questService;

    public void execute(CommandDto commandDto) {
        CommandName commandName = CommandName.valueOf(commandDto.getName());
        if (commandName == CommandName.GLOBAL_REFRESH) {
            return;
        }

        eventService.clear();
        AbstractCommand command = commandMapper.build(commandDto);
        command.execute();
        aiService.handleEvents();
    }

    public ResponseDto getState() {
        return new ResponseDto()
                .setLocalMapScreenData(localMapService.getScreenData())
                .setDialogScreenDataDto(dialogService.getScreenData())
                .setEventsScreenDataDto(eventService.getScreenData())
                .setQuestScreenDataDto(questService.getScreenData());
    }
}