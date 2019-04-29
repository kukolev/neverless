package neverless.window.vcl;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.Data;

@Data
public class ImageButton {

    private Canvas canvas;
    private Pane parentPane;
    private Image imgRegular;
    private Image imgMouseEntered;
    private Image imgMousePressed;
    private Image curImg;

    private EventHandler<MouseEvent> onClicked;

    /**
     * Constructor for button.
     *
     * @param parentPane        parent parentPane for this button.
     * @param imgRegular        image for regular state.
     * @param imgMouseEntered   image for state "mouse entered".
     * @param imgMousePressed   image for state "mouse pressed".
     */
    public ImageButton(Pane parentPane, Image imgRegular, Image imgMouseEntered, Image imgMousePressed) {
        this.parentPane = parentPane;
        this.imgRegular = imgRegular;
        this.imgMouseEntered = imgMouseEntered;
        this.imgMousePressed = imgMousePressed;

        canvas = new Canvas();
        canvas.setWidth(imgRegular.getWidth());
        canvas.setHeight(imgRegular.getHeight());

        canvas.setOnMouseEntered(this::onEmbeddedMouseEntered);
        canvas.setOnMouseExited(this::onEmbeddedMouseExited);
        canvas.setOnMousePressed(this::onEmbeddedMousePressed);
        canvas.setOnMouseReleased(this::onEmbeddedMouseReleased);
        canvas.setOnMouseClicked(this::onEmbeddedMouseClicked);

        parentPane.getChildren().add(canvas);
        setCurImg(imgRegular);
    }

    /**
     * Sets horizontal coordinate for button.
     *
     * @param x horizontal coordinate.
     */
    public void setLayoutX(int x) {
        canvas.setLayoutX(x);
    }

    /**
     * Sets vertical coordinate for button.
     *
     * @param y vertical coordinate.
     */
    public void setLayoutY(int y) {
        canvas.setLayoutY(y);
    }

    /**
     * Sets image for state "mouse entered".
     *
     * @param mouseEvent    data related to event.
     */
    private void onEmbeddedMouseEntered(MouseEvent mouseEvent) {
        setCurImg(imgMouseEntered);
    }

    /**
     * Sets image for state "mouse exited".
     *
     * @param mouseEvent    data related to event.
     */
    private void onEmbeddedMouseExited(MouseEvent mouseEvent) {
        setCurImg(imgRegular);
    }

    /**
     * Sets image for state "mouse pressed".
     *
     * @param mouseEvent    data related to event.
     */
    private void onEmbeddedMousePressed(MouseEvent mouseEvent) {
        setCurImg(imgMousePressed);
    }

    /**
     * Sets image for state "mouse released".
     *
     * @param mouseEvent    data related to event.
     */
    private void onEmbeddedMouseReleased(MouseEvent mouseEvent) {
        if (canvas.isHover()) {
            setCurImg(imgMouseEntered);
        } else {
            setCurImg(imgRegular);
        }
    }

    /**
     * Invokes user defined handle for event "mouse clicked".
     *
     * @param mouseEvent    data related to event.
     */
    private void onEmbeddedMouseClicked(MouseEvent mouseEvent) {
        if (onClicked != null) {
            onClicked.handle(mouseEvent);
        }
    }

    /**
     * Sets current image that should be drawn on canvas.
     *
     * @param img   current image.
     */
    private void setCurImg(Image img) {
        curImg = img;
        draw();
    }

    /**
     * Draws current image on canvas.
     */
    private void draw() {
        canvas.getGraphicsContext2D().drawImage(curImg, 0, 0);
    }
}