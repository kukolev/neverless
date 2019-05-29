package neverless.core;

import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class AbstractPane extends Pane {

    @Autowired
    private RootPane rootPane;

    private volatile boolean showing = false;
    private volatile boolean accept = false;
    private AbstractPane prevPane;

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
        prevPane = rootPane.getCurrentPane();
        rootPane.show(this);
        showing = true;
    }

    public boolean showModal() {
        show();
        while (showing) {
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return accept;
    }

    public void close(boolean accept) {
        showing = false;
        this.accept = accept;
        if (prevPane != null) {
            prevPane.show();
        }
    }
}
