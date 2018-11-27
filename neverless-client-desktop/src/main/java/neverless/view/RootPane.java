package neverless.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import neverless.controller.Controller;
import neverless.util.FrameExchanger;
import neverless.view.renderer.Frame;
import neverless.view.renderer.Renderer;
import neverless.view.renderer.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Exchanger;

@Component
public class RootPane extends Pane {

    @Autowired
    private Controller controller;
    @Autowired
    private Renderer renderer;
    @Autowired
    private FrameExchanger frameExchanger;

    @PostConstruct
    public void init() {
        Button button = new Button();
        button.setText("Start New Game");
        button.setMaxWidth(100);
        button.setMaxHeight(40);
        button.setOnMouseClicked(controller::startNewGameBtnClick);

        Canvas canvas = new Canvas(1000, 1000);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        this.getChildren().add(button);
        this.getChildren().add(canvas);

        setOnMouseClicked(controller::onClick);

        renderer.messageProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // System.out.println(newValue);

                try {
                    Frame frame = frameExchanger.exchange(null);
                    System.out.println(frame);
                    if (frame.getSprites().size() > 0) {

                        frame.getSprites().forEach(s -> s.draw(gc));
                    } else {
                       // gc.drawImage(image, 32, 32);
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

        });

        new Thread(renderer).start();
    }
}