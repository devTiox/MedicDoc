import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

    public MainWindow(String title) {
        this.setTitle(title);
        this.setSize(screenWidth, screenHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        this.add(mainPanel);
    }
}
