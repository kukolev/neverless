package neverless.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import neverless.controller.Controller;
import neverless.controller.vcl.ImageButton;
import neverless.model.Model;

import neverless.view.drawer.Drawer;
import neverless.view.drawer.DrawerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

import static neverless.util.Constants.CANVAS_HEIGHT;
import static neverless.util.Constants.CANVAS_WIDTH;

@Component
public class RootPane extends Pane {

    private static final int MOVE_BTN_GABARIT = 40;

    @Autowired
    private Controller controller;
    @Autowired
    private Model model;
    @Autowired
    private Drawer drawer;

    private ViewState viewState;
    private Pane localMapPane = new Pane();
    private Pane inventoryPane = new Pane();
    private Pane menuPane = new Pane();
    private List<Pane> paneList = new ArrayList<>();
    {
        paneList.add(localMapPane);
        paneList.add(inventoryPane);
        paneList.add(menuPane);
    }


    @PostConstruct
    public void init() {
        initInventory();
        initLocalMap();
        initMainMenu();

        this.getChildren().add(localMapPane);
        this.getChildren().add(inventoryPane);
        this.getChildren().add(menuPane);

        setViewState(ViewState.MAIN_MENU);
    }

    private void initLocalMap() {
        localMapPane.setLayoutX(0);
        localMapPane.setLayoutY(0);
        localMapPane.setMaxWidth(1000);
        localMapPane.setMaxHeight(1000);

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setLayoutX(20);
        canvas.setLayoutY(20);
        canvas.setOnMouseClicked(controller::onClick);

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

        TextArea infoArea = new TextArea();
        infoArea.setEditable(false);
        infoArea.setLayoutX(canvas.getLayoutX() + canvas.getWidth() + 20 + MOVE_BTN_GABARIT * 2);
        infoArea.setLayoutY(300);
        infoArea.setMaxWidth(150);
        infoArea.setMaxHeight(150);

        localMapPane.getChildren().add(btnMoveDown);
        localMapPane.getChildren().add(btnMoveUp);
        localMapPane.getChildren().add(btnMoveLeft);
        localMapPane.getChildren().add(btnMoveRight);
        localMapPane.getChildren().add(canvas);
        localMapPane.getChildren().add(infoArea);

        DrawerContext drawerContext = new DrawerContext()
                .setLocalMapCanvas(canvas)
                .setInfoArea(infoArea);

        drawer.setGraphicsContext(drawerContext);
        model.messageProperty().addListener(drawer);
    }

    private void initMainMenu() {
        menuPane.setLayoutX(0);
        menuPane.setLayoutY(0);
        menuPane.setMaxWidth(1000);
        menuPane.setMaxHeight(1000);

        ImageButton imageButton = new ImageButton(menuPane,
                new Image("buttons/btn_normal.png"),
                new Image("buttons/btn_over.png"),
                new Image("buttons/btn_pressed.png"));
        imageButton.setLayoutX(700);
        imageButton.setLayoutY(100);
        imageButton.setOnClicked(controller::startNewGameBtnClick);
    }

    private void initInventory() {
        inventoryPane.setLayoutX(0);
        inventoryPane.setLayoutY(0);
        inventoryPane.setMaxWidth(1000);
        inventoryPane.setMaxHeight(1000);
    }

    public void setViewState(ViewState viewState) {
        this.viewState = viewState;
        refrViewState();
    }

    public void refrViewState() {
        Pane displayPane = calcDisplayPane();
        paneList.forEach(p -> p.setVisible(p == displayPane));
    }

    private Pane calcDisplayPane() {
        Pane displayPane = localMapPane;
        switch (viewState) {
            case LOCAL_MAP: displayPane = localMapPane; break;
            case MAIN_MENU: displayPane = menuPane; break;
            case INVENTORY: displayPane = inventoryPane;
        }
        return displayPane;
    }
}