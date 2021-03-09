package ma.ac.inpt.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class Loading {
    public static DoubleProperty property_loading = new SimpleDoubleProperty(0);
    public static StringProperty stringProperty = new SimpleStringProperty("");
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Text text_loading;

    @FXML
    public void initialize() {
        progressBar.progressProperty().bindBidirectional(property_loading);
        text_loading.textProperty().bindBidirectional(stringProperty);

    }
}
