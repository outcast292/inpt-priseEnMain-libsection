package ma.ac.inpt.controllers;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import ma.ac.inpt.Libsection;
import ma.ac.inpt.exporter.PDF;
import ma.ac.inpt.setup.DB_connector;
import ma.ac.inpt.setup.Predictor;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;

public class Main {
    public static StringProperty output = new SimpleStringProperty("");
    public static File input = null;
    public String[] res = null;
    public boolean valide = false;
    @FXML
    private SplitPane centerPane;

    @FXML
    private CategoryAxis xAxisx;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<String, Float> chart_spectre;

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
                file_input.setText(file.getAbsolutePath());
                centerPane.setMouseTransparent(true);
                Predictor.predict();

            } catch (Exception e) {
                e.printStackTrace();
                Libsection.showAlert(Alert.AlertType.ERROR, "Erreur", "erreur pendant l'ouverture de fichier", e.getLocalizedMessage());
                valide = false;
            } finally {
                centerPane.setMouseTransparent(false);
            }
        }
    }

    @FXML
    void printReport(ActionEvent event) {
        if (valide) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Entrer le nom de ce scan");
            dialog.setHeaderText("ce nom va etre utilisé pour la tracabilite de fichier aussi");
            dialog.setContentText("Merci de saisir un nom valide");
// Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s -> {
                if (s.isEmpty())
                    Libsection.showAlert(Alert.AlertType.ERROR, "Erreur", null, "Nom de fichier ne doit pas etre nul");
                else {
                    try {
                        DB_connector.addEntrie(s, res);
                        ByteArrayOutputStream chart_spectr_bs = new ByteArrayOutputStream();
                        ByteArrayOutputStream chart_con_bs = new ByteArrayOutputStream();
                        ImageIO.write(SwingFXUtils.fromFXImage(chart_spectre.snapshot(new SnapshotParameters(), new WritableImage(400, 400)), null), "png", chart_spectr_bs);
                        ImageIO.write(SwingFXUtils.fromFXImage(chart_concentration.snapshot(new SnapshotParameters(), new WritableImage(400, 400)), null), "png", chart_con_bs);
                        Image chart_spectr_img = new Image(ImageDataFactory.create(chart_spectr_bs.toByteArray()));
                        Image chart_con_img = new Image(ImageDataFactory.create(chart_con_bs.toByteArray()));
                        PDF.printReport(s, new Date(), res, chart_spectr_img, chart_con_img);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Libsection.showAlert(Alert.AlertType.ERROR, "Erreur", "erreur pendant l'ouverture de fichier", e.getLocalizedMessage());

                    }
                }
            });

        } else {
            Libsection.showAlert(Alert.AlertType.WARNING, "alerte", null, "aucune donnee n'est charge");

        }

    }

    @FXML
    void seeHistorics(ActionEvent event) {
        Libsection.changeScene("Hist");
    }

    @FXML
    void showCredits(ActionEvent event) {
        Libsection.showAlert(Alert.AlertType.INFORMATION, "Credits", "Cette application a ete realise par ASEDS 2020-2023", "L'application permet de predire les concentration des elements a partir d'un csv de spectroscopy");
    }

    @FXML
    void close() {
        System.exit(0);
    }

    @FXML
    void initialize() {

        yAxis.setAutoRanging(true);
        yAxis.setLowerBound(0);
        yAxis.setTickUnit(20);
        chart_spectre.setCreateSymbols(false);

        output.addListener((observable, oldValue, newValue) -> {
            centerPane.setMouseTransparent(false);
            valide = true;
            res = newValue.split(";");
            p_input.setText(res[0]);
            mg_input.setText(res[1]);
            n_input.setText(res[2]);
            k_input.setText(res[3]);
            cu_input.setText(res[4]);
            chart_concentration.getData().clear();
            chart_spectre.getData().clear();
            XYChart.Series<String, Float> p = new XYChart.Series<>();
            p.getData().add(new XYChart.Data<>("P", Float.parseFloat(res[0])));
            p.getData().add(new XYChart.Data<>("Mg", Float.parseFloat(res[1])));
            p.getData().add(new XYChart.Data<>("N", Float.parseFloat(res[2])));
            p.getData().add(new XYChart.Data<>("K", Float.parseFloat(res[3])));
            p.getData().add(new XYChart.Data<>("Cu", Float.parseFloat(res[4])));
            chart_concentration.getData().add(p);
            try (CSVReader dataReader = new CSVReader(new FileReader(input))) {
                String[] nextLine;
                XYChart.Series<String, Float> series = new XYChart.Series<>();
                dataReader.readNext();
                int i = 0;
                while ((nextLine = dataReader.readNext()) != null) {
                    float population = Float.parseFloat(nextLine[0]);

                    series.getData().add(new XYChart.Data<>("" + (i++), population));

                }
                chart_spectre.getData().add(series);

            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }

        });
    }

}
