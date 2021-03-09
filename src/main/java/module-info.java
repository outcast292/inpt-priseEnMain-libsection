module ma.ac.inpt {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.swing;
    requires java.sql;
    requires java.desktop;
    requires zip4j;
    requires opencsv;
    requires layout;
    requires kernel;
    requires io;
    opens ma.ac.inpt.controllers to javafx.fxml;
    exports ma.ac.inpt;
}