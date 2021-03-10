package ma.ac.inpt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.ac.inpt.setup.PythonSetup;

import java.io.IOException;

public class Libsection extends Application {
    public static Stage stage;

    public static void main(String[] args) {

        launch(args);

    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Libsection.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void changeUtility(StageStyle st) {
        stage.close();
        stage = new Stage();
        stage.initStyle(st);
        stage.show();
    }

    public static void changeScene(String scene) {
        Scene sc;
        try {
            sc = new Scene(loadFXML(scene));
            stage.setScene(sc);
            stage.setTitle("LeManager");
            stage.show();
            stage.setResizable(false);
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(stage); // This sets the owner of this Dialog
        alert.showAndWait();
    }


    @Override
    public void start(Stage primaryStage) {
        Scene sc;
        try {
            stage = primaryStage;
            Pane p = (Pane) loadFXML("Loading");
            sc = new Scene(p);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("img/icone.png")));

            stage.setScene(sc);
            stage.setTitle("Libsection");
            stage.toFront();
            stage.centerOnScreen();
            stage.show();
/*
if(!PythonSetup.check_env()){
                System.out.println("env not setted up");
              //TODO fix this mess
            }*/


            PythonSetup.setup_env();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
