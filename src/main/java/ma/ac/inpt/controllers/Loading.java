package ma.ac.inpt.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Loading  {
    @FXML
    private  ProgressBar progressBar;

    @FXML
    private  Text text_loading;

    public static DoubleProperty property_loading = new SimpleDoubleProperty(0);
    public  static StringProperty stringProperty = new SimpleStringProperty("");



    @FXML
    public void initialize() {
        progressBar.progressProperty().bindBidirectional(property_loading);
        text_loading.textProperty().bindBidirectional(stringProperty);

        
    }
}
