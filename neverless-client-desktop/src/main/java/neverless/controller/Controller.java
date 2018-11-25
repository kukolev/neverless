package neverless.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import neverless.model.Command;
import neverless.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class Controller {

    @Autowired
    private Model model;

    @FXML
    public void onClick(MouseEvent event) {
        model.click(0, 0);
    }
}
