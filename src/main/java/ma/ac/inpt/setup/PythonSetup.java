package ma.ac.inpt.setup;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import ma.ac.inpt.Libsection;
import ma.ac.inpt.controllers.Loading;
import net.lingala.zip4j.ZipFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class PythonSetup {

    public static boolean check_env() {
        File zip = new File("python", "detection.py");
        return zip.exists();

    }

    public static void setup_env() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Process p = null;
                String output = "";
                try {
                    if(!check_env())
                        moveFiles();
                    Loading.property_loading.setValue(0.1d);
                    ProcessBuilder builder = new ProcessBuilder("python", "-m", "venv", "python/venv").inheritIO();
                    builder.redirectErrorStream(true);
                    builder.redirectOutput(ProcessBuilder.Redirect.PIPE);

                    p = builder.start();
                    InputStream processStdOut = p.getInputStream();
                    output = new String(processStdOut.readAllBytes());
                    p.waitFor();

                    Loading.property_loading.setValue(0.35d);
                    Loading.stringProperty.setValue("Telechargement des librairies");
                    builder = new ProcessBuilder("python/venv/Scripts/pip3.exe", "install", "-r", "python/requirements.txt").inheritIO();
                    builder.redirectErrorStream(true);
                    builder.redirectOutput(ProcessBuilder.Redirect.PIPE);
                    p = builder.start();
                    processStdOut = p.getInputStream();
                    output = new String(processStdOut.readAllBytes());
                    p.waitFor();
                    Loading.property_loading.setValue(0.95d);
                    Loading.stringProperty.setValue("Initialisation de la base de donnees");
                    DB_connector.init();
                    Thread.sleep(200);
                    Loading.property_loading.setValue(1d);
                    Loading.stringProperty.setValue("Lancement");
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    Libsection.showAlert(Alert.AlertType.ERROR, "Erreur", "erreur pendant l'initialisation", e.getLocalizedMessage());
                }
                System.out.println(output);
                return output;
            }

        };
        task.setOnSucceeded(event -> {
            //System.out.println("e " + task.getValue());
            Libsection.changeUtility(StageStyle.DECORATED);
            Libsection.changeScene("Main");

        });
        new Thread(task).start();

        //System.out.println(output);
    }

    public static void moveFiles() {
        File dir = new File("python");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File zip = new File(dir, "python_req.zip");
        if (!zip.exists()) {
            try {
                //InputStream scriptIn = getClass().getResourceAsStream("zip/python_req.zip");
                InputStream scriptIn = Libsection.loadRssourceAsStream("setup/python_req.zip");
                Files.copy(scriptIn, zip.toPath());
                String source = "python/python_req.zip";
                String destination = "python/";
                ZipFile zipFile = new ZipFile(source);
                zipFile.extractAll(destination);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}




