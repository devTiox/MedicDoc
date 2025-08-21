package Scenes;

import Containers.MainWindow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
        this.setOpaque(true);
        parentPanel.add(this,  panelName);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMenuScene();
            }
        });

    }

    private void backToMenuScene() {
        cardLayout.show(parentPanel,"MENU");
    }
}
