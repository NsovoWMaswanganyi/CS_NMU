package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    // controls initialised using reflection. name of field MUST be the same as the fx:id
    public TextField txtX;
    public TextField txtY;
    public Label lblResult;
    public Button btnCalculate;
    public Label lblProduct;

    // custom properties
    public IntegerProperty xProperty = new SimpleIntegerProperty(0);
    public IntegerProperty yProperty = new SimpleIntegerProperty(0);

    // event method linked using reflection. name of method MUST match the onAction field
        public void btnCalculateClicked(ActionEvent actionEvent) {
        int x = Integer.parseInt(txtX.getText());
        int y = Integer.parseInt(txtY.getText());
        int result = x + y;
        lblResult.setText("Sum = " + result);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // bind text fields to integer properties
        txtX.textProperty().bindBidirectional(xProperty, new NumberStringConverter());
        txtY.textProperty().bindBidirectional(yProperty, new NumberStringConverter());

        // bind label's text property to product of x and y properties
        lblProduct.textProperty().bind(
                Bindings.concat("Product = ", xProperty.multiply(yProperty).asString())
        );
    }
}
