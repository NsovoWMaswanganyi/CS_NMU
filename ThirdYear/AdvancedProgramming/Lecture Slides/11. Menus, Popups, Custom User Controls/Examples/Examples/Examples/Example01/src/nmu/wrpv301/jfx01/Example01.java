package nmu.wrpv301.jfx01;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Example01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private Stage stage = null;

    public Menu createFileMenu() {
        Menu fileMenu = new Menu("File");

        // Create new menu item (including an icon)
        Image icon = new Image(Example01.class.getResourceAsStream("/images/new.png"));
        MenuItem itemNew = new MenuItem("New...", new ImageView(icon));
        itemNew.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        itemNew.setOnAction(event -> System.out.println("New... menu item clicked."));

        // Create open menu item
        MenuItem itemOpen = new MenuItem("Open...");
        itemOpen.setOnAction(event -> System.out.println("Open... menu item clicked."));

        // Create exit menu item
        MenuItem itemExit = new MenuItem("Exit");
        itemExit.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        itemExit.setOnAction(event -> {
            System.out.println("Exit menu item clicked.");
            // Stop the application
            stage.hide();
        });

        // Add menu items to the menu
        fileMenu.getItems().addAll(
                itemNew,
                itemOpen,
                itemExit
        );

        return fileMenu;
    }

    public Menu createEditMenu() {
        Menu editMenu = new Menu("Edit");

        MenuItem itemCopy = new MenuItem("Copy");
        itemCopy.setOnAction(event -> System.out.println("Copy menu item clicked."));

        MenuItem itemCut = new MenuItem("Cut");
        itemCut.setOnAction(event -> System.out.println("Cut menu item clicked."));

        // Add menu items to the menu
        editMenu.getItems().addAll(
                itemCopy,
                itemCut,
                createPasteMenu()
        );

        return editMenu;
    }

    public Menu createPasteMenu() {
        Menu pasteMenu = new Menu("Paste as");

        MenuItem itemPasteAsText = new MenuItem("Text");
        itemPasteAsText.setOnAction(event -> System.out.println("Paste as Text menu item clicked."));

        MenuItem itemPasteAsContent = new MenuItem("Content");
        itemPasteAsContent.setOnAction(event -> System.out.println("Paste as Content menu item clicked."));

        MenuItem itemPasteAsFormatting = new MenuItem("Formatting");
        itemPasteAsFormatting.setOnAction(event -> System.out.println("Paste as Formatting menu item clicked."));


        // Add menu items to the menu
        pasteMenu.getItems().addAll(
                itemPasteAsText,
                itemPasteAsContent,
                itemPasteAsFormatting
        );

        return pasteMenu;
    }

    public MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(
                createFileMenu(),
                createEditMenu()
        );

        return menuBar;
    }

    public ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu(
                createFileMenu(),
                createEditMenu()
        );

        return contextMenu;
    }

    public MenuButton createMenuButton() {
        // Optional icon
        Image icon = new Image(Example01.class.getResourceAsStream("/images/new.png"));

        MenuButton button = new MenuButton("Menu Button", new ImageView(icon));
        button.setOnAction(event -> System.out.println("Menu Button clicked."));

        // Attach menu to the button
        button.getItems().addAll(createFileMenu().getItems());

        return button;
    }

    public SplitMenuButton createSplitMenuButton() {
        SplitMenuButton button = new SplitMenuButton(
                createFileMenu(),
                createEditMenu()
        );

        button.setText("Split Menu Button");
        Image icon = new Image(Example01.class.getResourceAsStream("/images/new.png"));
        button.setGraphic(new ImageView(icon));
        button.setOnAction(event -> System.out.println("Split Menu Button clicked."));

        return button;
    }

    private Rectangle createRectangle() {
        // Create a 100 x 75 rectangle
        Rectangle rect = new Rectangle(100, 75);

        // Create a linear gradient that goes from dark green -> green yellow -> blue
        Stop[] stops = new Stop[]{
                new Stop(0, Color.DARKGREEN),
                new Stop(0.5, Color.GREENYELLOW),
                new Stop(1, Color.CORNFLOWERBLUE)};
        LinearGradient linearGradient = new LinearGradient(0, 0, 0.5, 0.5, true, CycleMethod.REPEAT, stops);
        rect.setFill(linearGradient);

        return rect;
    }

    public Scene createScene() {
        BorderPane root = new BorderPane();

        VBox center = new VBox();
        center.setSpacing(10);

        Rectangle rect = createRectangle();

        center.getChildren().addAll(
                createMenuButton(),
                createSplitMenuButton(),
                rect
        );

        // Make the context menu appear when right-click on the center VBox
        ContextMenu contextMenu = createContextMenu();
        rect.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(stage, event.getScreenX(), event.getScreenY());
            }
        });

        // Set the menu bar to the top section, rest in center
        root.setTop(createMenuBar());
        root.setCenter(center);

        return new Scene(root, 500, 400);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        primaryStage.setTitle("Example 1");
        primaryStage.setScene(createScene());
        primaryStage.show();
    }
}
