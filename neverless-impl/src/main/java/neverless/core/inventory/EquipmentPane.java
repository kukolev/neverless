package neverless.core.inventory;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class EquipmentPane extends Pane {

    EquipmentPane() {
        Label titleLab = new Label();
        titleLab.setText("Equipment");
        titleLab.setLayoutX(20);
        titleLab.setLayoutY(20);

        this.setPrefWidth(300);
        this.setPrefWidth(300);
        this.getChildren().add(titleLab);
    }
}
