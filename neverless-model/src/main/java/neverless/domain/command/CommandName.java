package neverless.domain.command;

import org.springframework.web.bind.annotation.GetMapping;

import static neverless.domain.command.CommandType.GLOBAL;
import static neverless.domain.command.CommandType.LOCAL;

public enum CommandName {

    // Global commands
    GLOBAL_REFRESH(GLOBAL),
    GLOBAL_MAIN_MENU(GLOBAL),

    // Local commands
    MAIN_MENU_START_NEW_GAME(LOCAL),
    MAIN_MENU_LOAD_GAME(LOCAL),

    LOCAL_MAP_GO_LEFT(LOCAL),
    LOCAL_MAP_GO_RIGHT(LOCAL),
    LOCAL_MAP_GO_UP(LOCAL),
    LOCAL_MAP_GO_DOWN(LOCAL),
    LOCAL_MAP_START_DIALOG(LOCAL),

    DIALOG_SELECT_PHRASE(LOCAL);


    private CommandType commandType;

    CommandName(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return commandType;
    }
}