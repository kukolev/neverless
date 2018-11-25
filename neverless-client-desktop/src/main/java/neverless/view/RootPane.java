package neverless.view;

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
        setOnMouseClicked(controller::onClick);
    }
}