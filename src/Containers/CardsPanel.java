package Containers;

import javax.swing.*;
import java.awt.*;
import Scenes.*;

public class CardsPanel extends JPanel {
    public CardLayout cardLayout;
    public AddingPatientScene addingPatient;
    public PatientsListScene patientsList;

    public CardsPanel() {
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        this.setBackground(new Color(255,220,240));
        new MenuScene("MENU", cardLayout, this);
        patientsList = new PatientsListScene("PATIENTS_LIST", cardLayout, this);
        addingPatient = new AddingPatientScene("ADD_PATIENT", cardLayout, this);
        cardLayout.show(this, "MENU");
        this.revalidate();
        this.repaint();
    }

    public void updatePatientsListScene(){
        patientsList.revalidate();
        patientsList.repaint();
    }

    public void resetAddingScene(){
        addingPatient.reset();
    }

}
