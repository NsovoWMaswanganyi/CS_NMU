package nmu.wrpv301.jfx02;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class Controller {
    public Controller(Stage stage) {
        // Get a reference to the button.
        Button btnTooltip = (Button) stage.getScene().lookup("#button");

        // When button clicked, create and show the custom pop up.
        btnTooltip.setOnAction(event -> {
            CustomPopup popup = new CustomPopup("Time = " + LocalDateTime.now().toLocalTime());
            popup.show(stage);
        });
    }
}
