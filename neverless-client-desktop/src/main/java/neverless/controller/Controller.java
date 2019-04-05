package neverless.controller;

import javafx.scene.input.MouseEvent;
import neverless.model.CommandCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @Autowired
    private CommandCreator commandCreator;

    public void onClick(MouseEvent event) {
        commandCreator.click((int) event.getX(), (int) event.getY());
    }

    public void startNewGameBtnClick(MouseEvent event) {
        commandCreator.cmdStartNewGame();
    }
}
