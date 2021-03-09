package ma.ac.inpt.setup;

import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ma.ac.inpt.Libsection;
import ma.ac.inpt.controllers.Main;

import java.io.IOException;
import java.io.InputStream;

public class Predictor {
    public static void predict() {
        Parent parent = null;
        try {
            parent = Libsection.loadFXML("Processing");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        assert parent != null;
        stage.setScene(new Scene(parent));
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {

                Process p = null;
                String output = "";
                try {
                    ProcessBuilder builder = new ProcessBuilder("python/venv/Scripts/python.exe", "python/detection.py").inheritIO();
                    builder.redirectErrorStream(true);
                    builder.redirectOutput(ProcessBuilder.Redirect.PIPE);
                    p = builder.start();
                    InputStream processStdOut = p.getInputStream();
                    output = new String(processStdOut.readAllBytes());
                    p.waitFor();

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    Libsection.showAlert(Alert.AlertType.ERROR, "Erreur", "erreur pendant l'init", e.getLocalizedMessage());
                }
                System.out.println(output);
                return output;
            }

        };
        task.setOnSucceeded(event -> {
            stage.close();

            String[] str = ((String) task.getValue()).split(";");
            if (str.length != 5 || str[0].equals("Error")) {
                Libsection.showAlert(Alert.AlertType.ERROR, "Erreur", "erreur pendant l'init", str[2]);
            } else
                Main.output.setValue((String) task.getValue());

        });

        new Thread(task).start();

    }
}
