package neverless.core.inventory;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import neverless.domain.model.entity.item.weapon.AbstractHandEquipment;

public class EquipmentPane extends Pane {

    private ItemPane weaponPane;

    EquipmentPane(InventoryPane inventoryPane) {
        Label titleLab = new Label();
        titleLab.setText("Equipment");
        titleLab.setLayoutX(20);
        titleLab.setLayoutY(20);

        this.setPrefWidth(400);
        this.setPrefHeight(500);
        this.getChildren().add(titleLab);

        weaponPane = new ItemPane(null, inventoryPane, false);
        weaponPane.setLayoutX(20);
        weaponPane.setLayoutY(50);
        weaponPane.setPrefWidth(350);

        this.getChildren().add(weaponPane);
   }

    public void setWeapon(AbstractHandEquipment weapon) {
        weaponPane.setItem(weapon);
    }

    public AbstractHandEquipment getWeapon() {
        return (AbstractHandEquipment) weaponPane.getItem();
    }
}
