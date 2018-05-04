package neverless.domain;

import neverless.domain.Command;

import java.util.Arrays;
import java.util.List;

public class CommandCategories {

    public static final List<Command> LOCAL_MAP_COMMANDS = Arrays.asList(
            Command.SERVER_LOCAL_MAP_GO_DOWN,
            Command.SERVER_LOCAL_MAP_GO_LEFT,
            Command.SERVER_LOCAL_MAP_GO_RIGHT,
            Command.SERVER_LOCAL_MAP_GO_UP);
}
