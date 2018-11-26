package neverless.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import neverless.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @Autowired
    private Model model;

    @FXML
    public void onClick(MouseEvent event) {
        model.click(0, 0);
    }

    @FXML
    public void startNewGameBtnClick(MouseEvent event) {
        model.cmdStartNewGame();
    }
}
