package neverless.core.inventory;

import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryPaneController {

    @Autowired
    private InventoryPane inventoryPane;

    void cancelBtnOnClick(MouseEvent event) {
        inventoryPane.close(false);
    }

    void acceptBtnOnClick(MouseEvent event) {
        inventoryPane.close(true);
    }


}
