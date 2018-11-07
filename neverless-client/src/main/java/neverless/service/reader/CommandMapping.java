package neverless.service.reader;

// todo: .* should be changed to list particular of classes
import neverless.service.reader.impl.*;

import java.util.Arrays;

public enum CommandMapping {

    SERVER_GLOBAL_REFRESH( null, "rfr"),
    SERVER_GLOBAL_MENU_START_NEW_GAME( new StartNewGameCommandReader(), "sng"),
    SERVER_GLOBAL_WAIT(new WaitCommandReader(), "wait"),

    SERVER_LOCAL_MAP_GO_LEFT( new MapGoLeftCommandReader(), "mgl"),
    SERVER_LOCAL_MAP_GO_RIGHT( new MapGoRightCommandReader(), "mgr"),
    SERVER_LOCAL_MAP_GO_UP( new MapGoUpCommandReader(), "mgu"),
    SERVER_LOCAL_MAP_GO_DOWN( new MapGoDownCommandReader(), "mgd"),
    SERVER_LOCAL_START_DIALOG(new DialogStartCommandReader(), "sdlg"),
    SERVER_DIALOG_SELECT_PHRASE(new DialogSelectPhraseCommandReader(), "say"),
    SERVER_INVENTORY_EQUIP_RIGHT_HAND(new InventoryEquipRightHandCommandReader(), "erh"),
    SERVER_INVENTORY_EQUIP_LEFT_HAND(new InventoryEquipLeftHandCommandReader(), "elh"),
    SERVER_INVENTORY_CLEAR_RIGHT_HAND(new InventoryClearRightHandCommandReader(), "crh"),
    SERVER_INVENTORY_CLEAR_LEFT_HAND(new InventoryClearLeftHandCommandReader(), "clh"),

    CLIENT_EXIT(null, "exit"),
    CLIENT_VIEW_LOCAL_MAP(null, "1"),
    CLIENT_VIEW_INVENTORY(null, "2"),
    CLIENT_VIEW_JOURNAL(null, "3"),
    CLIENT_VIEW_DIALOG(null, "7"),
    CLIENT_VIEW_EVENTS(null, "8"),
    CLIENT_VIEW_MANUAL(null, "9"),
    CLIENT_VIEW_RESPONSE(null, "0");


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