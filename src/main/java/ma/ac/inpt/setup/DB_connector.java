package ma.ac.inpt.setup;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ma.ac.inpt.entity.Entry;

import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DB_connector {
    private static Connection cn = null;

    public static void init() throws SQLException {
        try {
            File db = new File("DB");
            if(!db.exists())
                db.mkdir();
            cn = DriverManager.getConnection("jdbc:sqlite:" + "DB/" + "database.db");
            if (!cn.isClosed() && cn != null) {
                Statement stmt = cn.createStatement();
                stmt.execute("CREATE TABLE IF NOT EXISTS entries (\n" +
                        "\tzone INTEGER NOT NULL,\n" +
                        "\tdate_entry TEXT  DEFAULT current_timestamp,\n" +
                        "\tp REAL NOT NULL,\n" +
                        "\tmg REAL NOT NULL,\n" +
                        "\tn REAL NOT NULL,\n" +
                        "\tk REAL NOT NULL,\n" +
                        "\tcu REAL NOT NULL\n" +
                        " );\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }

    }

    public static boolean addEntrie(String zone_name, String[] entry) throws SQLException {
        Boolean val = false;
        SimpleDateFormat dateformater = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PreparedStatement st = cn.prepareStatement("insert into entries values(?,?,?,?,?,?,?)");
            st.setString(1, zone_name);
            st.setString(2, dateformater.format(new Date()));
            st.setFloat(3, Float.parseFloat(entry[0]));
            st.setFloat(4, Float.parseFloat(entry[1]));
            st.setFloat(5, Float.parseFloat(entry[2]));
            st.setFloat(6, Float.parseFloat(entry[3]));
            st.setFloat(7, Float.parseFloat(entry[4]));
            val = st.execute();


        } catch (Exception e) {
            e.printStackTrace();
            throw  e;
        }
        return  val;
    }



    public static ObservableList<Entry> getEntries() {
        ObservableList<Entry> ops = FXCollections.observableArrayList();
        try {
            PreparedStatement st = cn.prepareStatement("select * from entries");
            SimpleDateFormat dateformater = new SimpleDateFormat("yyyy-MM-dd");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ops.add(new Entry(rs.getString(1), dateformater.parse(rs.getString(2)), rs.getFloat(3), rs.getFloat(4), rs.getFloat(5), rs.getFloat(6), rs.getFloat(7)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ops;
    }
}
