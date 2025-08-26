package Scenes;

import Containers.CardsPanel;
import Containers.MainWindow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class Scene extends JPanel {
    public CardLayout cardLayout;
    public String panelName;
    public CardsPanel parentPanel;
    public int screenWidth =  MainWindow.screenWidth;
    public int screenHeight = MainWindow.screenHeight;

    public Scene(String panelName, CardLayout cardLayout, CardsPanel parentPanel) {
        this.panelName = panelName;
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        this.setBackground(new Color(255,220,240));
        parentPanel.add(this,  panelName);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToPreviousScene();
            }
        });

    }

    private void backToPreviousScene() {
        if(this.getClass().getSimpleName().equals("PatientScene")) {
//            parentPanel.updatePatientsListScene();
            cardLayout.show(parentPanel, "PATIENTS_LIST");
//        }else if(this.getClass().getSimpleName().equals()){

        }else cardLayout.show(parentPanel,"MENU");
    }
}
