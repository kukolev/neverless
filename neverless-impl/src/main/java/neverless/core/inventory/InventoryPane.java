package neverless.core.inventory;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import neverless.core.AbstractPane;
import neverless.domain.model.entity.inventory.Inventory;
import neverless.domain.model.entity.item.AbstractItem;
import neverless.domain.model.entity.item.weapon.AbstractHandEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class InventoryPane extends AbstractPane {

    @Autowired
    private InventoryPaneController controller;

    private VBox lootItemBox = new VBox();
    private List<ItemPane> lootItemPanes = new ArrayList<>();
    private List<AbstractItem> lootItems = new ArrayList<>();

    private VBox bagItemBox = new VBox();
    private List<ItemPane> bagItemPanes = new ArrayList<>();

    private EquipmentPane equipmentPane = new EquipmentPane(this);

    private Inventory inventory = new Inventory();

    @Override
    protected void setup() {
        Label equipmentLab = new Label();
        equipmentLab.setText("Equipment");
        equipmentLab.setLayoutX(200);
        equipmentLab.setLayoutY(200);

        equipmentPane.setLayoutX(200);
        equipmentPane.setLayoutY(220);
        equipmentPane.setPrefWidth(300);
        equipmentPane.setPrefHeight(450);

        // Bag items
        Label bagLab = new Label();
        bagLab.setText("Bag");
        bagLab.setLayoutX(520);
        bagLab.setLayoutY(200);

        bagItemBox.setSpacing(10);
        bagItemBox.setPadding(new Insets(10));
        ScrollPane bagScroll = new ScrollPane(bagItemBox);
        bagScroll.setFitToWidth(true);
        bagScroll.setLayoutX(520);
        bagScroll.setLayoutY(220);
        bagScroll.setPrefWidth(300);
        bagScroll.setMaxHeight(450);
        bagScroll.setPannable(true);

        // Loot items
        Label lootLab = new Label();
        lootLab.setText("Loot");
        lootLab.setLayoutX(840);
        lootLab.setLayoutY(200);

        lootItemBox.setSpacing(10);
        lootItemBox.setPadding(new Insets(10));
        ScrollPane lootScroll = new ScrollPane(lootItemBox);
        lootScroll.setFitToWidth(true);
        lootScroll.setLayoutX(840);
        lootScroll.setLayoutY(220);
        lootScroll.setPrefWidth(300);
        lootScroll.setMaxHeight(450);
        lootScroll.setPannable(true);

        Button acceptBtn = new Button();
        acceptBtn.setLayoutX(200);
        acceptBtn.setLayoutY(700);
        acceptBtn.setPrefWidth(200);
        acceptBtn.setText("Accept");
        acceptBtn.setOnMouseClicked(controller::acceptBtnOnClick);

        Button takeAllBtn = new Button();
        takeAllBtn.setLayoutX(420);
        takeAllBtn.setLayoutY(700);
        takeAllBtn.setPrefWidth(200);
        takeAllBtn.setText("Take All");
        takeAllBtn.setOnMouseClicked(controller::takeAllBtnOnClick);

        Button cancelBtn = new Button();
        cancelBtn.setLayoutX(640);
        cancelBtn.setLayoutY(700);
        cancelBtn.setPrefWidth(100);
        cancelBtn.setText("Cancel");
        cancelBtn.setOnMouseClicked(controller::cancelBtnOnClick);

        this.getChildren().add(cancelBtn);
        this.getChildren().add(acceptBtn);
        this.getChildren().add(takeAllBtn);
        this.getChildren().add(bagScroll);
        this.getChildren().add(lootScroll);
        this.getChildren().add(equipmentPane);
        this.getChildren().add(equipmentLab);
        this.getChildren().add(bagLab);
        this.getChildren().add(lootLab);
    }

    public void init(List<AbstractItem> lootItems, Inventory inventory) {
        this.lootItems.clear();
        this.lootItems.addAll(lootItems);
        copyInventories(inventory, this.inventory);
        refresh();
    }

    public void take(AbstractItem item) {
        if (item == null) {
            return;
        }
        if (lootItems.contains(item)) {
            lootItems.remove(item);
            inventory.getBag().addLast(item);
            refresh();
        }
    }

    public void takeAll() {
        if (!lootItems.isEmpty()) {
            lootItems.forEach(item -> {
                inventory.getBag().addLast(item);
            });
            lootItems.clear();
            refresh();
        }
    }

    public void drop(AbstractItem item) {
        if (item == null) {
            return;
        }
        if (inventory.getBag().getItems().contains(item)) {
            lootItems.add(item);
            inventory.getBag().getItems().remove(item);
        } else if (item == equipmentPane.getWeapon()) {
            inventory.getBag().addLast(item);
            inventory.getEquipment().setWeapon(null);
        }
        refresh();
    }

    public void equip(AbstractItem item) {
        if (item == null) {
            return;
        }
        // Define list where item stored
        List<AbstractItem> itemList;
        if (inventory.getBag().getItems().contains(item)) {
            itemList = inventory.getBag().getItems();
        } else if (lootItems.contains(item)) {
            itemList = lootItems;
        } else {
            return;
        }

        if (item instanceof AbstractHandEquipment) {
            AbstractHandEquipment weapon = inventory.getEquipment().getWeapon();
            inventory.getEquipment().setWeapon((AbstractHandEquipment) item);
            if (weapon != null) {
                inventory.getBag().addLast(weapon);
            }
            itemList.remove(item);
        }
        refresh();
    }

    private void refresh() {
        initScrollBox(inventory.getBag().getItems(), bagItemBox, bagItemPanes);
        initScrollBox(lootItems, lootItemBox, lootItemPanes);
        equipmentPane.setWeapon(inventory.getEquipment().getWeapon());
    }

    private void initScrollBox(List<AbstractItem> items, VBox vbox, List<ItemPane> panes) {
        if (items == null) {
            return;
        }
        Platform.runLater(() -> {
                    vbox.getChildren().clear();
                    panes.clear();
                    for (int i = 0; i < items.size(); i++) {
                        ItemPane itemPane = new ItemPane(items.get(i), this, false);
                        panes.add(itemPane);
                        vbox.getChildren().add(itemPane);
                        itemPane.refresh();
                    }
                }
        );
    }

    public void copyToInventory(Inventory inventory) {
        copyInventories(this.inventory, inventory);
    }

    public void copyToLootItems(List<AbstractItem> lootItems) {
        lootItems.clear();
        lootItems.addAll(this.lootItems);
    }

    private void copyInventories(Inventory source, Inventory dest) {
        // Copy items from bag
        dest.getBag().getItems().clear();
        source.getBag()
                .getItems()
                .stream()
                .forEach(it -> dest.getBag().addLast(it));
        // Copy equipment
        AbstractHandEquipment handEquipment = source.getEquipment().getWeapon();
        dest.getEquipment().setWeapon(handEquipment);
    }
}