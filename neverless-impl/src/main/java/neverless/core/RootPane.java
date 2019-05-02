package neverless.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import neverless.service.controller.Controller;
import neverless.util.vcl.ImageButton;

import neverless.service.view.Drawer;
import neverless.service.view.DrawerContext;
import neverless.domain.view.ViewState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

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
public class RootPane extends Pane {

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
        localMapPane.setPrefWidth(Integer.MAX_VALUE);
        localMapPane.setPrefHeight(Integer.MAX_VALUE);

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setLayoutX(CANVAS_LEFT);
        canvas.setLayoutY(CANVAS_TOP);
        canvas.setOnMouseClicked(controller::onClick);
        canvas.setOnMouseMoved(controller::onMouseMoved);

        TextArea statsArea = new TextArea();
        statsArea.setEditable(false);
        statsArea.setLayoutX(STATS_LEFT);
        statsArea.setLayoutY(STATS_TOP);
        statsArea.setPrefWidth(STATS_WIDTH);
        statsArea.setPrefHeight(STATS_HEIGHT);

        TextArea infoArea = new TextArea();
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



        localMapPane.getChildren().add(canvas);
        localMapPane.getChildren().add(infoArea);
        localMapPane.getChildren().add(statsArea);
        localMapPane.getChildren().add(pauseBtn);

        DrawerContext drawerContext = new DrawerContext()
                .setLocalMapCanvas(canvas)
                .setStatesArea(statsArea)
                .setLogArea(infoArea);

        drawer.setDrawerContext(drawerContext);
        model.messageProperty().addListener(drawer);
    }

    private void initMainMenu() {
        menuPane.setLayoutX(0);
        menuPane.setLayoutY(0);
        menuPane.setPrefWidth(Integer.MAX_VALUE);
        menuPane.setPrefHeight(Integer.MAX_VALUE);

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
        inventoryPane.setPrefWidth(Integer.MAX_VALUE);
        inventoryPane.setPrefHeight(Integer.MAX_VALUE);
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