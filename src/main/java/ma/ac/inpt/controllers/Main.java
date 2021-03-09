package ma.ac.inpt.controllers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import ma.ac.inpt.Libsection;
import ma.ac.inpt.setup.Predictor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Main {
    public static StringProperty output = new SimpleStringProperty("");
    public static File input = null;
    @FXML
    private CategoryAxis xAxisx;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<?, ?> chart_spectre;

    @FXML
    private TextField file_input;

    @FXML
    private BarChart<String, Float> chart_concentration;

    @FXML
    private TextField p_input;

    @FXML
    private TextField mg_input;

    @FXML
    private TextField n_input;

    @FXML
    private TextField k_input;

    @FXML
    private TextField cu_input;

    @FXML
    void openFileExplorer(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("csv", "*.csv")
        );
        File file = fileChooser.showOpenDialog(Libsection.stage);
        if (file != null) {
            input = new File("python/input.csv");
            try {
                Files.copy(file.toPath(), input.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Predictor.predict();

            } catch (Exception e) {
                e.printStackTrace();
                Libsection.showAlert(Alert.AlertType.ERROR, "Erreur", "erreur pendant l'ouverture de fichier", e.getLocalizedMessage());
            }
        }
    }

    @FXML
    void printReport(ActionEvent event) {

    }

    @FXML
    void seeHistorics(ActionEvent event) {

    }

    @FXML
    void showCredits(ActionEvent event) {

    }

    @FXML
    void close() {
        System.exit(0);
    }

    @FXML
    void initialize() {

        yAxis.setAutoRanging(true);
        yAxis.setLowerBound(0);
        yAxis.setTickUnit(5);
        chart_spectre.setCreateSymbols(false);

        output.addListener((observable, oldValue, newValue) -> {
            String[] str = newValue.split(";");
            p_input.setText(str[0]);
            mg_input.setText(str[1]);
            n_input.setText(str[2]);
            k_input.setText(str[3]);
            cu_input.setText(str[4]);
            chart_concentration.getData().removeAll();
            XYChart.Series<String, Float> p = new XYChart.Series<>();
            p.getData().add(new XYChart.Data<>("P", Float.parseFloat(str[0])));
            p.getData().add(new XYChart.Data<>("MG", Float.parseFloat(str[1])));
            p.getData().add(new XYChart.Data<>("N", Float.parseFloat(str[2])));
            p.getData().add(new XYChart.Data<>("K", Float.parseFloat(str[3])));
            p.getData().add(new XYChart.Data<>("CU", Float.parseFloat(str[4])));
            chart_concentration.getData().add(p);
            try (CSVReader dataReader = new CSVReader(new FileReader(input))) {
                String[] nextLine;
                XYChart.Series series = new XYChart.Series();
                dataReader.readNext();
                while ((nextLine = dataReader.readNext()) != null) {
                    float population = Float.parseFloat(nextLine[0]);

                    series.getData().add(new XYChart.Data(""+population,population ));

                }
                chart_spectre.getData().add(series);

            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }

        });
    }

}
