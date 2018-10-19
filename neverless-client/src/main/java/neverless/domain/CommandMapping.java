package neverless.domain;

import neverless.service.reader.CommandReader;
import neverless.service.reader.impl.*;

import java.util.Arrays;

public enum CommandMapping {

    SERVER_GLOBAL_REFRESH( null, "rfr"),
    SERVER_GLOBAL_MENU_START_NEW_GAME( new StartNewGameCommandReader(), "sng"),
    SERVER_LOCAL_MAP_GO_LEFT( new MapGoLeftCommandReader(), "mgl"),
    SERVER_LOCAL_MAP_GO_RIGHT( new MapGoRightCommandReader(), "mgr"),
    SERVER_LOCAL_MAP_GO_UP( new MapGoUpCommandReader(), "mgu"),
    SERVER_LOCAL_MAP_GO_DOWN( new MapGoDownCommandReader(), "mgd"),
    SERVER_LOCAL_START_DIALOG(new DialogStartCommandReader(), "sdlg"),
    SERVER_DIALOG_SELECT_PHRASE(new DialogSelectPhraseCommandReader(), "say"),

    CLIENT_VERSION( null, "clver"),
    CLIENT_EXIT(null, "exit");

    CommandMapping(CommandReader reader, String shortName) {
        this.reader = reader;
        this.shortName = shortName;
    }

    private CommandReader reader;
    private String shortName;

    public String getShortName() {
        return shortName;
    }

    public CommandReader getReader() {
        return reader;
    }

    public static CommandMapping findByShortName(String shortName) {
        return Arrays.stream(CommandMapping.values())
                .filter(cmd -> cmd.shortName.equals(shortName))
                .findFirst()
                .orElse(null);
    }
}