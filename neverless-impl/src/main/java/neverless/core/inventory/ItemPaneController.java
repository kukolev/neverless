package neverless.core.inventory;

import javafx.scene.input.MouseEvent;

public class ItemPaneController {

    private ItemPane pane;

    ItemPaneController(ItemPane pane) {
        this.pane = pane;
    }

    public void areaBtnOnClick(MouseEvent event) {
        pane.showContextMenu(event.getScreenX(), event.getScreenY());
    }
}
