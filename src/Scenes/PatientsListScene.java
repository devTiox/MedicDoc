package Scenes;


import Containers.CardsPanel;
import java.awt.CardLayout;
import java.util.*;

public class PatientsListScene extends Scene {

    public static List<Patient> patientsList = new ArrayList<>();

    public PatientsListScene(String title, CardLayout cardLayout, CardsPanel parentPanel) {
        super(title, cardLayout, parentPanel);

        Collections.sort(patientsList);
    }
}

