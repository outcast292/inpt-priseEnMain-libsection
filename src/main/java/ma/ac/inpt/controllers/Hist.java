package ma.ac.inpt.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import ma.ac.inpt.Libsection;
import ma.ac.inpt.entity.Entry;
import ma.ac.inpt.setup.DB_connector;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Hist {
    @FXML
    private Text cntr;

    @FXML
    private TableView<Entry> tableView;

    @FXML
    private TableColumn<Entry, Number> n_entry;

    @FXML
    private TableColumn<Entry, String> zone_name;

    @FXML
    private TableColumn<Entry, Date> date_entry;

    @FXML
    private TableColumn<Entry, Float> p;

    @FXML
    private TableColumn<Entry, Float> mg;

    @FXML
    private TableColumn<Entry, Float> n;

    @FXML
    private TableColumn<Entry, Float> k;

    @FXML
    private TableColumn<Entry, Float> cu;

    @FXML
    void initialize() {

        n_entry.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(column.getValue()) + 1));
        zone_name.setCellValueFactory(column -> column.getValue().zoneProperty());
        date_entry.setCellValueFactory(column -> column.getValue().date_entryProperty());
        p.setCellValueFactory(column -> column.getValue().pProperty().asObject());
        mg.setCellValueFactory(column -> column.getValue().mgProperty().asObject());
        n.setCellValueFactory(column -> column.getValue().nProperty().asObject());
        k.setCellValueFactory(column -> column.getValue().kProperty().asObject());
        cu.setCellValueFactory(column -> column.getValue().cuProperty().asObject());
        date_entry.setCellFactory(column -> {
            TableCell<Entry, Date> cell = new TableCell<>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        tableView.setItems(DB_connector.getEntries());
        String[] s = cntr.getText().split(":");
        cntr.setText(s[0] + ": " + tableView.getItems().size() + " Entree" + (tableView.getItems().size() > 1 ? "s" : ""));

    }

    @FXML
    void goBack(ActionEvent event) {
        Libsection.changeScene("Main");
    }

}
