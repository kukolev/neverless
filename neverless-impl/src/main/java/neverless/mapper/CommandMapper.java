package neverless.mapper;

import neverless.service.command.AbstractCommand;
import neverless.service.command.DialogSelectPhraseCommand;
import neverless.service.command.DialogStartCommand;
import neverless.service.command.MapGoCommand;
import neverless.service.command.MenuStartNewGameCommand;
import neverless.service.command.RefreshCommand;
import neverless.domain.command.CommandName;
import neverless.domain.constants.DirectionEnum;
import neverless.dto.CommandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CommandMapper {

    @Autowired
    private ApplicationContext context;

    public AbstractCommand build(CommandDto commandDto) {
        CommandName commandName = CommandName.valueOf(commandDto.getName());
        AbstractCommand command = null;
        switch (commandName) {
            case LOCAL_MAP_GO_UP: command = buildMapGoCommand(DirectionEnum.UP); break;
            case LOCAL_MAP_GO_DOWN: command = buildMapGoCommand(DirectionEnum.DOWN); break;
            case LOCAL_MAP_GO_LEFT: command = buildMapGoCommand(DirectionEnum.LEFT); break;
            case LOCAL_MAP_GO_RIGHT: command = buildMapGoCommand(DirectionEnum.RIGHT); break;
            case LOCAL_MAP_START_DIALOG: command = context.getBean(DialogStartCommand.class); break;
            case DIALOG_SELECT_PHRASE: command = context.getBean(DialogSelectPhraseCommand.class); break;
            case MAIN_MENU_START_NEW_GAME: command = context.getBean(MenuStartNewGameCommand.class); break;
            case GLOBAL_REFRESH: command = context.getBean(RefreshCommand.class);
        }
        command.init(commandDto.getBundle());
        return command;
    }

    private AbstractCommand buildMapGoCommand(DirectionEnum direction) {
        MapGoCommand command = context.getBean(MapGoCommand.class);
        command.setDirection(direction);
        return command;
    }
}
