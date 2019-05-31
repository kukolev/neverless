package neverless.core.inventory;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import neverless.domain.model.entity.item.AbstractItem;
import org.apache.logging.log4j.util.Strings;

public class ItemPane extends HBox {

    private Button takeBtn = new Button();
    private Button dropBtn = new Button();
    private Button equipBtn = new Button();
    private Button areaBtn;

    private ItemPaneController controller = new ItemPaneController(this);

    private AbstractItem item;
    private InventoryPane inventoryPane;

    ItemPane(AbstractItem item, InventoryPane inventoryPane, boolean buttonRight) {
        this.setSpacing(5);
        this.setPadding(new Insets(5));
        this.setPrefWidth(Integer.MAX_VALUE);

        this.item = item;
        this.inventoryPane = inventoryPane;

        takeBtn.setMinWidth(40);
        takeBtn.setPrefHeight(20);
        takeBtn.setLayoutX(5);
        takeBtn.setLayoutY(5);
        takeBtn.setOnMouseClicked(controller::takeBtnOnClick);

        dropBtn.setMinWidth(40);
        dropBtn.setPrefHeight(20);
        dropBtn.setLayoutX(5);
        dropBtn.setLayoutY(5);
        dropBtn.setOnMouseClicked(controller::dropBtnOnClick);

        equipBtn.setMinWidth(40);
        equipBtn.setPrefHeight(20);
        equipBtn.setLayoutX(5);
        equipBtn.setLayoutY(5);
        equipBtn.setOnMouseClicked(controller::equipBtnOnClick);

        areaBtn = new Button();
        areaBtn.setPrefWidth(Integer.MAX_VALUE);
        areaBtn.setPrefHeight(20);
        areaBtn.setLayoutX(5);
        areaBtn.setLayoutY(5);

        equipBtn.setText("E");
        takeBtn.setText("T");
        dropBtn.setText("D");

        if (buttonRight) {
            this.getChildren().add(areaBtn);
            this.getChildren().add(equipBtn);
            this.getChildren().add(takeBtn);
            this.getChildren().add(dropBtn);
        } else {
            this.getChildren().add(equipBtn);
            this.getChildren().add(takeBtn);
            this.getChildren().add(dropBtn);
            this.getChildren().add(areaBtn);
        }
        refresh();
    }

    public void drop() {
        inventoryPane.drop(item);
    }

    public void take() {
        inventoryPane.take(item);
    }

    public void equip() {
        inventoryPane.equip(item);
    }


    public void setItem(AbstractItem item) {
        this.item = item;
        refresh();
    }

    public AbstractItem getItem() {
        return item;
    }

    public void refresh() {
        if (item != null) {
            Platform.runLater(() -> areaBtn.setText(item.getTitle()));
        } else {
            Platform.runLater(() -> areaBtn.setText(Strings.EMPTY));
        }
    }
}