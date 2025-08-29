package Scenes;


import Containers.CardsPanel;
import Data.Patient;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PatientsListScene extends Scene {

    public static List<Patient> patientsList = new ArrayList<>();

    public PatientsListScene(String title, CardLayout cardLayout, CardsPanel parentPanel) {
        super(title, cardLayout, parentPanel);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 30));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        Collections.sort(patientsList);
        setUpButtons();
    }

    private void setUpButtons(){
        for(Patient patient : patientsList){
            new PatientScene(patient.patientSceneTitle, cardLayout, parentPanel, patient);
            String buttonLabel = "<html>%s %s<br> %s</html>".formatted(patient.lastName, patient.name, patient.PESEL);
            JButton patientButton = new  JButton(buttonLabel);
            patientButton.addActionListener(e -> cardLayout.show(parentPanel, patient.patientSceneTitle));
            this.add(patientButton);
        }
    }
}

