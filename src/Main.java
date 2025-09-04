
import Containers.MainWindow;
import Data.DataBase;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DataBase.connection();
                DataBase.createTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new MainWindow("MedicDoc");
        });
    }
}
