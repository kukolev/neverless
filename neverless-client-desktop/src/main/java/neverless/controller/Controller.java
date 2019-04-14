package neverless.controller;

import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @Autowired
    private ControlHandler controlHandler;

    public void onClick(MouseEvent event) {
        controlHandler.click((int) event.getX(), (int) event.getY());
    }

    public void onMouseMoved(MouseEvent event) {
        controlHandler.mouseMove((int) event.getX(), (int) event.getY());
    }

    public void startNewGameBtnClick(MouseEvent event) {
        controlHandler.cmdStartNewGame();
    }
}
