import javax.swing.*;
import java.awt.*;

public abstract class Scenes extends JPanel {
    public CardLayout cardLayout;
    public String panelName;

    public Scenes(String panelName, CardLayout cardLayout, JPanel mainPanel) {
        this.panelName = panelName;
        this.cardLayout = cardLayout;
        mainPanel.add(this,  panelName);
    }
}
