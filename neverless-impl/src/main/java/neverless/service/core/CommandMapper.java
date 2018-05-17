package neverless.service.core;

import neverless.service.command.AbstractCommand;
import neverless.service.command.DialogSelectPhraseCommand;
import neverless.service.command.DialogStartCommand;
import neverless.service.command.MapGoDownCommand;
import neverless.service.command.MapGoLeftCommand;
import neverless.service.command.MapGoRightCommand;
import neverless.service.command.MapGoUpCommand;
import neverless.service.command.MapStartFightCommand;
import neverless.service.command.MenuStartNewGameCommand;
import neverless.service.command.GlobalRefreshCommand;
import neverless.dto.command.CommandName;
import neverless.dto.command.CommandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static neverless.dto.command.CommandName.DIALOG_SELECT_PHRASE;
import static neverless.dto.command.CommandName.GLOBAL_REFRESH;
import static neverless.dto.command.CommandName.LOCAL_MAP_GO_DOWN;
import static neverless.dto.command.CommandName.LOCAL_MAP_GO_LEFT;
import static neverless.dto.command.CommandName.LOCAL_MAP_GO_RIGHT;
import static neverless.dto.command.CommandName.LOCAL_MAP_GO_UP;
import static neverless.dto.command.CommandName.LOCAL_MAP_START_DIALOG;
import static neverless.dto.command.CommandName.LOCAL_MAP_START_FIGHT;
import static neverless.dto.command.CommandName.MENU_START_NEW_GAME;

@Service
public class CommandMapper {

    @Autowired
    private ApplicationContext context;

    private Map<CommandName, Class<? extends AbstractCommand>> map = new HashMap<>();
    {
        map.put(LOCAL_MAP_GO_UP, MapGoUpCommand.class);
        map.put(LOCAL_MAP_GO_DOWN, MapGoDownCommand.class);
        map.put(LOCAL_MAP_GO_LEFT, MapGoLeftCommand.class);
        map.put(LOCAL_MAP_GO_RIGHT, MapGoRightCommand.class);
        map.put(LOCAL_MAP_START_DIALOG, DialogStartCommand.class);
        map.put(LOCAL_MAP_START_FIGHT, MapStartFightCommand.class);
        map.put(DIALOG_SELECT_PHRASE, DialogSelectPhraseCommand.class);
        map.put(MENU_START_NEW_GAME, MenuStartNewGameCommand.class);
        map.put(GLOBAL_REFRESH, GlobalRefreshCommand.class);
    }

    public AbstractCommand build(CommandDto commandDto) {
        CommandName commandName = CommandName.valueOf(commandDto.getName());
        Class<? extends AbstractCommand> clazz = map.get(commandName);
        AbstractCommand command = context.getBean(clazz);
        command.init(commandDto.getBundle());
        return command;
    }
}
