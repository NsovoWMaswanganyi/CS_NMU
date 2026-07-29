package nmu.wrap301.jfx03;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.swing.plaf.ColorUIResource;
import java.util.Random;

public class Example03 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private Scene createScene() {
        BorderPane borderPane = new BorderPane();

        Pane pane = new Pane();
        BackgroundFill background = new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY);
        pane.setBackground(new Background(background));
        pane.setId("pane");

        Random random = new Random();
        for(int i=0; i<3; i++) {
            DieCatcher dieCatcher = new DieCatcher();
            dieCatcher.setLayoutX(random.nextInt(400));
            dieCatcher.setLayoutY(random.nextInt(400));
            dieCatcher.setWidth(85);
            dieCatcher.setHeight(85);
            dieCatcher.setFill(Color.LIGHTGREEN);
            dieCatcher.setStroke(Color.DARKGREEN);
            dieCatcher.setStrokeWidth(5);
            pane.getChildren().add(dieCatcher);
        }

        // Label to display the total
        Label lblTotal = new Label("Total");
        lblTotal.setId("total");

        borderPane.setCenter(pane);
        borderPane.setBottom(lblTotal);

        return new Scene(borderPane);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Example 3");
        primaryStage.setScene(createScene());
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);

        Controller controller = new Controller(primaryStage);

        primaryStage.show();
    }
}
