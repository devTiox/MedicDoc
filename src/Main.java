import Containers.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow("MedicDoc"); // lub jak tam startujesz
        });
    }
}
