package neverless.core.inventory;

import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryPaneController {

    @Autowired
    private InventoryPane inventoryPane;

    void closeBtnOnClick(MouseEvent event) {
        inventoryPane.close();
    }
}
