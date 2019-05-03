package neverless.core;

import javafx.scene.image.Image;
import neverless.service.controller.Controller;
import neverless.util.vcl.ImageButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuPane extends AbstractPane {

    @Autowired
    private Controller controller;

    @Override
    protected void setup() {
        ImageButton imageButton = new ImageButton(this,
                new Image("buttons/btn_normal.png"),
                new Image("buttons/btn_over.png"),
                new Image("buttons/btn_pressed.png"));
        imageButton.setLayoutX(700);
        imageButton.setLayoutY(100);
        imageButton.setOnClicked(controller::startNewGameBtnClick);
    }
}
