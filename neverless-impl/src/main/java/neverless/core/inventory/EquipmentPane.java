package neverless.core.inventory;

import javafx.scene.layout.Pane;
import neverless.domain.model.entity.item.weapon.AbstractHandEquipment;

public class EquipmentPane extends Pane {

    private ItemPane weaponPane;

    EquipmentPane(InventoryPane inventoryPane) {
        this.setPrefWidth(300);
        this.setPrefHeight(500);

        weaponPane = new ItemPane(null, inventoryPane, false);
        weaponPane.setLayoutX(0);
        weaponPane.setLayoutY(20);
        weaponPane.setPrefWidth(300);

        this.getChildren().add(weaponPane);
   }

    public void setWeapon(AbstractHandEquipment weapon) {
        weaponPane.setItem(weapon);
    }

    public AbstractHandEquipment getWeapon() {
        return (AbstractHandEquipment) weaponPane.getItem();
    }
}
