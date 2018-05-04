package neverless.util;

import neverless.domain.game.dialog.event.AbstractDialogEvent;
import neverless.domain.game.dialog.predicate.AvailablePlayerPhrasePredicate;
import neverless.domain.game.dialog.predicate.NpcStartingPhrasePredicate;

public class Constants {

    public static final String PLAYER_NAME = "Vova";
    public static final int PLAYER_START_X = 50;
    public static final int PLAYER_START_Y = 50;

    public static final NpcStartingPhrasePredicate ALWAYS_START = () -> true;
    public static final AvailablePlayerPhrasePredicate ALWAYS_AVAILABLE = () -> true;
    public static final AbstractDialogEvent EMPTY_EVENT = () -> {};
}
