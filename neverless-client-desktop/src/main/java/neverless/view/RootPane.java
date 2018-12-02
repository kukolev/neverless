package neverless.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import neverless.controller.Controller;
import neverless.view.renderer.Renderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static neverless.util.Constants.CANVAS_HEIGHT;
import static neverless.util.Constants.CANVAS_WIDTH;

@Component
public class RootPane extends Pane {

    private static final int MOVE_BTN_GABARIT = 40;

    @Autowired
    private Controller controller;
    @Autowired
    private Renderer renderer;
    @Autowired
    private Drawer drawer;

    @PostConstruct
    public void init() {
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setLayoutX(20);
        canvas.setLayoutY(20);
        canvas.setOnMouseClicked(controller::onClick);

        Button btnStartNewGame = new Button();
        btnStartNewGame.setText("Start New Game");
        btnStartNewGame.setLayoutX(canvas.getLayoutX() + canvas.getWidth() + 20);
        btnStartNewGame.setMaxWidth(100);
        btnStartNewGame.setLayoutY(0);
        btnStartNewGame.setOnMouseClicked(controller::startNewGameBtnClick);

        Button btnMoveDown = new Button();
        btnMoveDown.setText("D");
        btnMoveDown.setLayoutX(canvas.getLayoutX() + canvas.getWidth() + 20 + MOVE_BTN_GABARIT);
        btnMoveDown.setLayoutY(200 + MOVE_BTN_GABARIT);
        btnMoveDown.setMaxWidth(MOVE_BTN_GABARIT);
        btnMoveDown.setOnMouseClicked(controller::moveDownBtnClick);

        Button btnMoveUp = new Button();
        btnMoveUp.setText("U");
        btnMoveUp.setLayoutX(canvas.getLayoutX() + canvas.getWidth() + 20 + MOVE_BTN_GABARIT);
        btnMoveUp.setLayoutY(200 - MOVE_BTN_GABARIT);
        btnMoveUp.setMaxWidth(MOVE_BTN_GABARIT);
        btnMoveUp.setOnMouseClicked(controller::moveUpBtnClick);

        Button btnMoveLeft = new Button();
        btnMoveLeft.setText("L");
        btnMoveLeft.setLayoutX(canvas.getLayoutX() + canvas.getWidth() + 20);
        btnMoveLeft.setLayoutY(200);
        btnMoveLeft.setMaxWidth(MOVE_BTN_GABARIT);
        btnMoveLeft.setOnMouseClicked(controller::moveLeftBtnClick);

        Button btnMoveRight = new Button();
        btnMoveRight.setText("R");
        btnMoveRight.setLayoutX(canvas.getLayoutX() + canvas.getWidth() + 20 + MOVE_BTN_GABARIT * 2);
        btnMoveRight.setLayoutY(200);
        btnMoveRight.setMaxWidth(MOVE_BTN_GABARIT);
        btnMoveRight.setOnMouseClicked(controller::moveRightBtnClick);

        this.getChildren().add(btnStartNewGame);
        this.getChildren().add(btnMoveDown);
        this.getChildren().add(btnMoveUp);
        this.getChildren().add(btnMoveLeft);
        this.getChildren().add(btnMoveRight);
        this.getChildren().add(canvas);


        drawer.setGraphicsContext(canvas.getGraphicsContext2D());
        renderer.messageProperty().addListener(drawer);
        new Thread(renderer).start();
    }
}