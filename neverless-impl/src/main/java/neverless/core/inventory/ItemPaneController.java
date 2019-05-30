package neverless.core.inventory;

import javafx.scene.input.MouseEvent;

public class ItemPaneController {

    private ItemPane pane;

    ItemPaneController(ItemPane pane) {
        this.pane = pane;
    }

    public void takeBtnOnClick(MouseEvent event) {
        pane.take();
    }

    public void dropBtnOnClick(MouseEvent event) {
        pane.drop();
    }

    public void equipBtnOnClick(MouseEvent event) {
        pane.equip();
    }
}