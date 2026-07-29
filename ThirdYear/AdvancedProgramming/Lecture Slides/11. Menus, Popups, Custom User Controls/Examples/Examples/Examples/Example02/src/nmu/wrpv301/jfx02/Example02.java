package nmu.wrpv301.jfx02;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;



public class Example02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public Scene createScene() {
        // Create root of the scene graph.
        VBox root = new VBox();
        root.setSpacing(5);
        root.setPadding(new Insets(10));

        // Create button & attach a tool tip
        Button btnTooltip = new Button("Tooltip Button");
        btnTooltip.setId("button");
        btnTooltip.setTooltip(new Tooltip("This button has a tool tip :)"));

        root.getChildren().addAll(
                btnTooltip
        );

        return new Scene(root, 300, 200);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Example 2");
        primaryStage.setScene(createScene());

        Controller controller = new Controller(primaryStage);

        primaryStage.show();
    }
}
