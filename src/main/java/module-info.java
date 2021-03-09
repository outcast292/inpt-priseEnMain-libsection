module ma.ac.inpt {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires java.desktop;
    requires    zip4j;
    requires    opencsv;
    opens ma.ac.inpt.controllers to javafx.fxml;
    exports ma.ac.inpt;
}