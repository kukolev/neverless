package neverless.dto.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CommandName {

    // Global commands
    GLOBAL_REFRESH(CommandType.GLOBAL),
    GLOBAL_MAIN_MENU(CommandType.GLOBAL),

    // Local commands
    MAIN_MENU_START_NEW_GAME(CommandType.LOCAL),
    MAIN_MENU_LOAD_GAME(CommandType.LOCAL),
    LOCAL_MAP_GO_LEFT(CommandType.LOCAL),
    LOCAL_MAP_GO_RIGHT(CommandType.LOCAL),
    LOCAL_MAP_GO_UP(CommandType.LOCAL),
    LOCAL_MAP_GO_DOWN(CommandType.LOCAL),
    LOCAL_MAP_START_DIALOG(CommandType.LOCAL, "npcX", "npcY"),
    DIALOG_SELECT_PHRASE(CommandType.LOCAL, "phraseNumber");

    private CommandType commandType;
    private List<String> paramNames = new ArrayList<>();

    CommandName(CommandType commandType, String ... paramNames) {
        this.commandType = commandType;
        this.paramNames.addAll(Arrays.asList(paramNames));
    }

    public CommandType getCommandType() {
        return commandType;
    }
}