package neverless.core.inventory;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import neverless.domain.model.entity.item.AbstractItem;

public class ItemPane extends HBox {

    private Button actionBtn = new Button();
    private ItemPaneController controller = new ItemPaneController(this);
    private AbstractItem item;

    private Button areaBtn;

    ItemPane(AbstractItem item, boolean buttonRight) {
        this.setSpacing(5);
        this.setPadding(new Insets(5));
        this.setPrefWidth(Integer.MAX_VALUE);

        actionBtn.setPrefWidth(20);
        actionBtn.setPrefHeight(20);
        actionBtn.setLayoutX(5);
        actionBtn.setLayoutY(5);

        areaBtn = new Button();
        areaBtn.setPrefWidth(Integer.MAX_VALUE);
        areaBtn.setPrefHeight(20);
        areaBtn.setLayoutX(5);
        areaBtn.setLayoutY(5);
        areaBtn.setOnMouseClicked(controller::areaBtnOnClick);

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(new MenuItem("Equip"));
        contextMenu.getItems().add(new MenuItem("Drop"));
        areaBtn.setContextMenu(contextMenu);

        if (buttonRight) {
            this.getChildren().add(areaBtn);
            this.getChildren().add(actionBtn);
            actionBtn.setText(">");
        } else {
            this.getChildren().add(actionBtn);
            this.getChildren().add(areaBtn);
            actionBtn.setText("<");
        }

        refresh();
    }

    public void setItem(AbstractItem item) {
        this.item = item;
        refresh();
    }

    private void refresh() {
        if (item == null) {
            return;
        }
        Platform.runLater(() -> areaBtn.setText(item.getTitle()));
    }

    public void showContextMenu(double screenX, double screenY) {
        actionBtn.getContextMenu().show(actionBtn, screenX, screenY);
    }
}