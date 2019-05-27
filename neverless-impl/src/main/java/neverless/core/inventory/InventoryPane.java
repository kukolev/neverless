package neverless.core.inventory;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import neverless.core.AbstractPane;
import neverless.domain.model.entity.item.AbstractItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class InventoryPane extends AbstractPane {

    @Autowired
    private InventoryPaneController controller;

    private VBox content = new VBox();
    private List<ItemPane> lootItemPanes = new ArrayList<>();

    private List<AbstractItem> lootItems = new ArrayList<>();

    @Override
    protected void setup() {
        Button closeBtn = new Button();
        closeBtn.setLayoutX(1000);
        closeBtn.setLayoutY(800);
        closeBtn.setText("Close");
        closeBtn.setOnMouseClicked(controller::closeBtnOnClick);

        EquipmentPane equipmentPane = new EquipmentPane();
        equipmentPane.setLayoutX(100);
        equipmentPane.setLayoutY(100);

        content.setSpacing(10);
        content.setPadding(new Insets(10));

        for (int i = 0; i < 100; i++) {
            ItemPane itemPane = new ItemPane(null, true);
            lootItemPanes.add(itemPane);
            content.getChildren().add(itemPane);
        }

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setLayoutX(1000);
        scrollPane.setLayoutY(100);
        scrollPane.setPrefWidth(400);
        scrollPane.setMaxHeight(200);
        scrollPane.setPannable(true);
        this.getChildren().add(closeBtn);
        this.getChildren().add(scrollPane);
        this.getChildren().add(equipmentPane);
    }

    public void init(List<AbstractItem> lootItems) {
        this.lootItems = lootItems;
        refresh();
    }

    private void refresh() {
        if (lootItems == null) {
            return;
        }
        for (int i = 0; i < lootItems.size(); i++) {
            lootItemPanes.get(i).setItem(lootItems.get(i));
        }
    }
}
