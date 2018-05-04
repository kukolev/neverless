package neverless.service;

import lombok.AllArgsConstructor;
import neverless.domain.command.CommandName;
import neverless.mapper.CommandMapper;
import neverless.service.command.AbstractCommand;
import neverless.dto.CommandDto;
import neverless.dto.ResponseDto;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommandRouterService {

    private CommandMapper commandMapper;
    private RenderService renderService;
    private DialogService dialogService;
    private EventService eventService;
    private AiService aiService;

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
                .setLocalMapScreenData(renderService.calcLocalMap())
                .setDialogScreenDataDto(dialogService.getScreenData())
                .setEventsScreenDataDto(eventService.getScreenData());
    }
}