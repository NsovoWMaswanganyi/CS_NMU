package nmu.wrpv301.jfx02;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Popup;

/**
 * CustomPopup is a control with a custom appearance and effects applied.
 * It is draggable by the title bar and can be closed by right clicking
 * on the title bar.
 */
public class CustomPopup extends Popup {
    // region Fields
    // A cached reference to the part of the control can be dragged by.
    private VBox topBox;
    // Message displayed on the control.
    private String message;
    // The initial point where the mouse was pressed.
    private Double anchorX, anchorY;
    // endregion

    public CustomPopup(String message) {
        // Very important - need to perform inherited initialisation.
        super();
        this.message = message;

        anchorX = new Double(0);
        anchorY = new Double(0);

        // Create the layout for this control.
        createLayout();
        // Logic for handling drag and drop behaviour.
        setBehaviour();
    }

    protected void createLayout() {
        StackPane stackPane = new StackPane();

        // region Add drop shadow effect to stack pane
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);
        shadow.setColor(new Color(0, 0, 0, 0.5));
        stackPane.setEffect(shadow);
        // endregion

        // region Create the title bar
        Label lblTitle= new Label("Custom Popup");
        topBox = new VBox();
        topBox.setPadding(new Insets(5));
        topBox.getChildren().add(lblTitle);
        Stop[] stops = new Stop[]{
                new Stop(0, Color.DARKGRAY),
                new Stop(1, Color.BLACK)};
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        BackgroundFill titleBackground = new BackgroundFill(linearGradient, CornerRadii.EMPTY, Insets.EMPTY);
        topBox.setBackground(new Background(titleBackground));
        lblTitle.setTextFill(Color.WHITE);
        // endregion

        // region Place everything into the border pane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topBox);
        borderPane.setPadding(new Insets(5));
        BackgroundFill dlgBackground = new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY);
        borderPane.setBackground(new Background(dlgBackground));
        borderPane.setBorder(new Border(new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                new CornerRadii(5),
                new BorderWidths(2))));
        // endregion

        // region Add message to center of popup
        Label lblMessage = new Label(message);
        borderPane.setCenter(lblMessage);
        // endregion

        // Stack controls on top of each other
        stackPane.getChildren().addAll(borderPane);

        getContent().add(stackPane);
    }

    protected void setBehaviour() {
        // region Dragging behaviour.
        // When press a mouse button, mark position of cursor relative to control.
        topBox.setOnMousePressed(event -> {
            // If not dragging with "left" mouse button, don't proceed.
            if(event.getButton() != MouseButton.PRIMARY) return;

            // Remember offset/displacement/anchor relative to pop up's top left corner
            anchorX = event.getScreenX() - getX();
            anchorY = event.getScreenY() - getY();
        });

        // When mouse dragged (mouse button down & moved), update the position of control.
        topBox.setOnMouseDragged(event -> {
            // If not dragging with "left" mouse button, don't proceed.
            if(event.getButton() != MouseButton.PRIMARY) return;

            // Set top left corner of pop up so looks like moving at cursor

            setX(event.getScreenX() - anchorX);
            setY(event.getScreenY() - anchorY);

        });
        // endregion

        // Close popup if title bar clicked with secondary mouse button
        topBox.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.SECONDARY) hide();
        });
    }
}
