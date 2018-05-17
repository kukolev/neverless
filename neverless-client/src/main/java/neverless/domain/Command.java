package neverless.domain;

import neverless.dto.command.CommandName;

import java.util.Arrays;

public enum Command {

    SERVER_GLOBAL_REFRESH(CommandType.GAME_COMMAND, CommandName.GLOBAL_REFRESH, "rfr"),
    SERVER_GLOBAL_MENU_START_NEW_GAME(CommandType.GAME_COMMAND, CommandName.MENU_START_NEW_GAME, "sng"),
    SERVER_LOCAL_MAP_GO_LEFT(CommandType.GAME_COMMAND, CommandName.LOCAL_MAP_GO_LEFT, "mgl"),
    SERVER_LOCAL_MAP_GO_RIGHT(CommandType.GAME_COMMAND, CommandName.LOCAL_MAP_GO_RIGHT, "mgr"),
    SERVER_LOCAL_MAP_GO_UP(CommandType.GAME_COMMAND, CommandName.LOCAL_MAP_GO_UP, "mgu"),
    SERVER_LOCAL_MAP_GO_DOWN(CommandType.GAME_COMMAND, CommandName.LOCAL_MAP_GO_DOWN, "mgd"),
    SERVER_LOCAL_START_DIALOG(CommandType.GAME_COMMAND, CommandName.LOCAL_MAP_START_DIALOG, "sdlg", "npcX", "npcY"),
    SERVER_DIALOG_SELECT_PHRASE(CommandType.GAME_COMMAND, CommandName.DIALOG_SELECT_PHRASE, "say", "phraseNumber"),

    CLIENT_VERSION(CommandType.LOCAL_COMMAND, null, "clver"),
    CLIENT_EXIT(CommandType.LOCAL_COMMAND, null, "exit");

    Command(CommandType commandType, CommandName serverCommand, String shortName, String ... params) {
        this.commandType = commandType;
        this.serverCommand = serverCommand;
        this.shortName = shortName;
        this.params = params;
    }

    private CommandType commandType;
    private CommandName serverCommand;
    private String shortName;
    private String[] params;

    public String getShortName() {
        return shortName;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public CommandName getServerCommand() {
        return serverCommand;
    }

    public static Command findByShortName(String shortName) {
        return Arrays.stream(Command.values())
                .filter(cmd -> cmd.shortName.equals(shortName))
                .findFirst()
                .orElse(null);
    }

    public String[] getParams() {
        return params;
    }
}