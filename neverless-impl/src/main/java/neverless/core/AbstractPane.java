package neverless.core;

import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class AbstractPane extends Pane {

    @Autowired
    private RootPane rootPane;

    @PostConstruct
    private void init() {
        rootPane.register(this);
        setup();
    }

    /**
     * Initializes all content of the pane.
     * Method should be overwritten in inheritors.
     */
    abstract protected void setup();

    /**
     * Shows current pane.
     * Method calls rootPane for showing.
     */
    public void show() {
        rootPane.show(this);
    }
}
