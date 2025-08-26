package Scenes;

import Containers.CardsPanel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PatientScene extends Scene{
    private final Patient patient;


    public PatientScene(String title, CardLayout cardLayout, CardsPanel parentPanel, Patient patient) {
        super(title, cardLayout, parentPanel);
        this.setLayout(new BorderLayout());
        this.patient = patient;
        this.add(getPatientInfoArea(), BorderLayout.NORTH);
        this.add(documentationPanel(), BorderLayout.CENTER);
    }

    private JTextArea getPatientInfoArea(){
        JTextArea patientInfoArea;
        @SuppressWarnings("SpellCheckingInspection") String patientInfo = """
                
                    Imię i nazwisko: %s %s
                    Data urodzenia: %s     Wiek: %d
                    Miejsce zamieszkania: %s
                    PESEL: %s
                    Numer telefonu: %s    E-mail: %s
                    -----------------------------------------
                """.formatted(patient.name, patient.lastName, patient.birthDate, getAge(), patient.address,
                patient.PESEL, patient.phoneNumber, patient.email);
        patientInfoArea = new JTextArea(patientInfo);
        patientInfoArea.setEditable(false);
        patientInfoArea.setOpaque(false);
        this.add(patientInfoArea);
        return patientInfoArea;
    }

    private int getAge(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDate = LocalDate.parse(patient.birthDate, dtf);
        LocalDate now = LocalDate.now();
        return now.getYear() - birthDate.getYear();
    }

    private JScrollPane documentationPanel(){
        JTextArea documentationArea = new JTextArea(patient.documentation);
        documentationArea.setOpaque(false);
        documentationArea.setMargin(new java.awt.Insets(0, 30, 0, 0));
        JScrollPane documentationPanel = new JScrollPane(documentationArea);
        documentationPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        documentationPanel.setOpaque(false);
        documentationPanel.getViewport().setOpaque(false);

        return documentationPanel;
    }


}
