package Containers;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public static int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

    public MainWindow(String title) {
        this.setTitle(title);
        this.setSize(screenWidth, screenHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(new CardsPanel());
    }
}
