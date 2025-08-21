package Scenes;


import javax.swing.*;
import java.awt.CardLayout;
import java.text.Collator;
import java.util.*;

public class PatientsListScene extends Scene {

    public static List<Patient> patientsList = new ArrayList<>();

    public PatientsListScene(String title, CardLayout cardLayout, JPanel parentPanel) {
        super(title, cardLayout, parentPanel);

        Collator polishCollator = Collator.getInstance(new Locale("pl", "PL"));
        patientsList.sort(Comparator.comparing(Patient::lastName, polishCollator).thenComparing(Patient:: name, polishCollator));
        System.out.println("Scene " + panelName + " initialized. Components=" + getComponentCount());

    }
}
