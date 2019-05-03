package neverless.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import lombok.Getter;
import neverless.service.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static neverless.util.Constants.CANVAS_HEIGHT;
import static neverless.util.Constants.CANVAS_LEFT;
import static neverless.util.Constants.CANVAS_TOP;
import static neverless.util.Constants.CANVAS_WIDTH;
import static neverless.util.Constants.LOG_HEIGHT;
import static neverless.util.Constants.LOG_LEFT;
import static neverless.util.Constants.LOG_TOP;
import static neverless.util.Constants.LOG_WIDTH;
import static neverless.util.Constants.PAUSE_BTN_HEIGHT;
import static neverless.util.Constants.PAUSE_BTN_LEFT;
import static neverless.util.Constants.PAUSE_BTN_TOP;
import static neverless.util.Constants.PAUSE_BTN_WIDTH;
import static neverless.util.Constants.STATS_HEIGHT;
import static neverless.util.Constants.STATS_LEFT;
import static neverless.util.Constants.STATS_TOP;
import static neverless.util.Constants.STATS_WIDTH;

@Component
public class LocalMapPane extends AbstractPane {

    @Autowired
    private Controller controller;

    @Getter
    private Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    @Getter
    private TextArea statsArea = new TextArea();
    @Getter
    private TextArea infoArea = new TextArea();

    @Override
    protected void setup() {
        canvas.setLayoutX(CANVAS_LEFT);
        canvas.setLayoutY(CANVAS_TOP);
        canvas.setOnMouseClicked(controller::onClick);
        canvas.setOnMouseMoved(controller::onMouseMoved);

        statsArea.setEditable(false);
        statsArea.setLayoutX(STATS_LEFT);
        statsArea.setLayoutY(STATS_TOP);
        statsArea.setPrefWidth(STATS_WIDTH);
        statsArea.setPrefHeight(STATS_HEIGHT);

        infoArea.setEditable(false);
        infoArea.setLayoutX(LOG_LEFT);
        infoArea.setLayoutY(LOG_TOP);
        infoArea.setPrefWidth(LOG_WIDTH);
        infoArea.setPrefHeight(LOG_HEIGHT);

        Button pauseBtn = new Button();
        pauseBtn.setText("Pause");
        pauseBtn.setLayoutX(PAUSE_BTN_LEFT);
        pauseBtn.setLayoutY(PAUSE_BTN_TOP);
        pauseBtn.setPrefWidth(PAUSE_BTN_WIDTH);
        pauseBtn.setPrefHeight(PAUSE_BTN_HEIGHT);
        pauseBtn.setOnMouseClicked(controller::onPauseBtnClick);

        this.getChildren().add(canvas);
        this.getChildren().add(infoArea);
        this.getChildren().add(statsArea);
        this.getChildren().add(pauseBtn);
    }
}
