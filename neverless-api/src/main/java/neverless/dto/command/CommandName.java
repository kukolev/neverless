package neverless.dto.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CommandName {

    // Global commands
    GLOBAL_REFRESH(),

    // Local commands
    MENU_START_NEW_GAME(),
    LOCAL_MAP_GO_LEFT(),
    LOCAL_MAP_GO_RIGHT(),
    LOCAL_MAP_GO_UP(),
    LOCAL_MAP_GO_DOWN(),
    LOCAL_MAP_START_DIALOG("npcX", "npcY"),
    LOCAL_MAP_START_FIGHT("npcX", "npcY"),
    DIALOG_SELECT_PHRASE("phraseNumber");

    private List<String> paramNames = new ArrayList<>();

    CommandName(String ... paramNames) {
        this.paramNames.addAll(Arrays.asList(paramNames));
    }
}