package Scenes;

import Containers.MainWindow;
import javax.swing.*;
import java.awt.*;

public abstract class Scene extends JPanel {
    public CardLayout cardLayout;
    public String panelName;
    public JPanel parentPanel;
    public int screenWidth =  MainWindow.screenWidth;
    public int screenHeight = MainWindow.screenHeight;

    public Scene(String panelName, CardLayout cardLayout, JPanel parentPanel) {
        this.panelName = panelName;
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        this.setBackground(new Color(255,220,240));
        parentPanel.add(this,  panelName);
    }

//    public void backToMainPanel() {
//        cardLayout.show(parentPanel,"MENU");
//    }
}
