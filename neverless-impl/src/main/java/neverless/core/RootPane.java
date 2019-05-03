package neverless.core;

import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class RootPane extends Pane {

    private List<Pane> paneList = new ArrayList<>();

    /**
     * Registers pane in local list.
     * Initializes basic properties for the pane.
     *
     * @param pane  pane to register.
     */
    public void register(AbstractPane pane) {
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        pane.setPrefWidth(Integer.MAX_VALUE);
        pane.setPrefHeight(Integer.MAX_VALUE);
        paneList.add(pane);
        this.getChildren().add(pane);
    }

    /**
     * Shows registered pane and hides all other panes.
     *
     * @param pane  pane that should be showed.
     */
    public void show(AbstractPane pane) {
        paneList.forEach(p -> p.setVisible(p == pane));
    }
}