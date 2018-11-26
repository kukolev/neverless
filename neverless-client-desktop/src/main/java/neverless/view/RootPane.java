package neverless.view;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import neverless.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RootPane extends Pane {

    @Autowired
    private Controller controller;

    @PostConstruct
    public void init() {
        Button button = new Button();
        button.setText("Start New Game");
        button.setMaxWidth(100);
        button.setMaxHeight(40);
        button.setOnMouseClicked(controller::startNewGameBtnClick);
        this.getChildren().add(button);
        setOnMouseClicked(controller::onClick);
    }
}