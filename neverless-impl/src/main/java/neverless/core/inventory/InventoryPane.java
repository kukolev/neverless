package neverless.core.inventory;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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

    private Inventory inventory = new Inventory();

    @Override
    protected void setup() {
        Button acceptBtn = new Button();
        acceptBtn.setLayoutX(100);
        acceptBtn.setLayoutY(800);
        acceptBtn.setText("Accept");
        acceptBtn.setOnMouseClicked(controller::acceptBtnOnClick);

        Button cancelBtn = new Button();
        cancelBtn.setLayoutX(1000);
        cancelBtn.setLayoutY(800);
        cancelBtn.setText("Cancel");
        cancelBtn.setOnMouseClicked(controller::cancelBtnOnClick);

        EquipmentPane equipmentPane = new EquipmentPane();
        equipmentPane.setLayoutX(100);
        equipmentPane.setLayoutY(100);

        // Bag items
        bagItemBox.setSpacing(10);
        bagItemBox.setPadding(new Insets(10));
        ScrollPane bagScroll = new ScrollPane(bagItemBox);
        bagScroll.setFitToWidth(true);
        bagScroll.setLayoutX(500);
        bagScroll.setLayoutY(100);
        bagScroll.setPrefWidth(400);
        bagScroll.setMaxHeight(200);
        bagScroll.setPannable(true);

        // Loot items
        lootItemBox.setSpacing(10);
        lootItemBox.setPadding(new Insets(10));
        ScrollPane lootScroll = new ScrollPane(lootItemBox);
        lootScroll.setFitToWidth(true);
        lootScroll.setLayoutX(1000);
        lootScroll.setLayoutY(100);
        lootScroll.setPrefWidth(400);
        lootScroll.setMaxHeight(200);
        lootScroll.setPannable(true);

        this.getChildren().add(cancelBtn);
        this.getChildren().add(acceptBtn);
        this.getChildren().add(bagScroll);
        this.getChildren().add(lootScroll);
        this.getChildren().add(equipmentPane);
    }

    public void init(List<AbstractItem> lootItems, Inventory inventory) {
        this.lootItems = lootItems;
        // Copy items from bag
        this.inventory.getBag().getItems().clear();
        inventory.getBag()
                .getItems()
                .stream()
                .forEach(it -> this.inventory.getBag().addLast(it));
        // Copy equipment
        AbstractHandEquipment handEquipment = inventory.getEquipment().getWeapon();
        inventory.getEquipment().setWeapon(handEquipment);

        refresh();
    }

    private void refresh() {
        initScrollBox(inventory.getBag().getItems(), bagItemBox, bagItemPanes);
        initScrollBox(lootItems, lootItemBox, lootItemPanes);
    }

    private void initScrollBox(List<AbstractItem> items, VBox vbox, List<ItemPane> panes) {
        if (items == null) {
            return;
        }
        Platform.runLater(() -> {
                    vbox.getChildren().clear();
                    panes.clear();
                    for (int i = 0; i < items.size(); i++) {
                        ItemPane itemPane = new ItemPane(items.get(i), false);
                        panes.add(itemPane);
                        vbox.getChildren().add(itemPane);
                        itemPane.refresh();
                    }
                }
        );
    }
}