package neverless.controller;

import javafx.scene.input.MouseEvent;
import neverless.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @Autowired
    private Model model;

    public void onClick(MouseEvent event) {
        model.click((int) event.getX(), (int) event.getY());
    }

    public void startNewGameBtnClick(MouseEvent event) {
        model.cmdStartNewGame();
    }

    public void moveDownBtnClick(MouseEvent event) {
        model.cmdMapGoDown();
    }

    public void moveUpBtnClick(MouseEvent event) {
        model.cmdMapGoUp();
    }

    public void moveLeftBtnClick(MouseEvent event) {
        model.cmdMapGoLeft();
    }

    public void moveRightBtnClick(MouseEvent event) {
        model.cmdMapGoRight();
    }
}
