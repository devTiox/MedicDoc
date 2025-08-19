package Containers;

import javax.swing.*;
import java.awt.*;
import Scenes.*;

public class CardsPanel extends JPanel {
    public CardLayout cardLayout;

    public CardsPanel() {
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        this.setBackground(Color.PINK);
        new MenuScene("MENU", cardLayout, this);
        new PatientsListScene("PATIENTS_LIST", cardLayout, this);
        new AddingPatientScene("ADD_PATIENT", cardLayout, this);
        this.revalidate();
        this.repaint();
    }
}
