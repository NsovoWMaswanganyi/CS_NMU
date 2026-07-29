package nmu.wrap301.jfx03;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {
    // region Fields
    // A binding that will calculate the total of all the die rolled.
    private NumberBinding total = null;
    // Cached references to controls.
    private Pane pane;
    private Label lblTotal;
    // endregion

    private Node lastNode = null;

    public Controller(Stage stage) {
        pane = (Pane) stage.getScene().lookup("#pane");
        lblTotal = (Label) stage.getScene().lookup("#total");

        // Right-click pane to add a new die at that location
        pane.setOnMouseClicked(event -> {
            // If not the right mouse button, exit immediately.
            if (event.getButton() != MouseButton.SECONDARY) return;

            // region Create new Die and add to playing area.
            // Create a new Die
            Die die = new Die();

            // Want to center die on click, die is 64x64.
            die.setX(event.getSceneX() - 32);
            die.setY(event.getSceneY() - 32);

            // Add die to the pane
            pane.getChildren().addAll(die);
            // endregion

            // region Update sum number binding and label to display it.
            // Update the total binding
            if (total == null) {
                // First die rolled, so create a new binding to it, with calculation
                // 0 + die's value
                total = (NumberBinding) Bindings.add(0, die.valueProperty());
            } else {
                // Other die already rolled, so get a new binding - note, a new object is returned,
                // so need to update the label's text binding
                total = total.add(die.valueProperty());
            }

            // Rebind label to the new total binding
            lblTotal.textProperty().bind(
                    Bindings.concat("Total = ").concat(total)
            );
            // endregion
        });

        /*
        pane.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            double posX = event.getSceneX();
            double posY = event.getSceneY();

            Node overNode = null;

            for(Node node : pane.getChildren()) {
                Bounds bounds = node.getBoundsInParent();
                if(bounds.contains(posX, posY)) overNode = node;
            }

            if(lastNode != overNode) {
                System.out.println(posX + ", " + posY + " = " + overNode);
            }

            lastNode = overNode;
        });
         */
    }
}
