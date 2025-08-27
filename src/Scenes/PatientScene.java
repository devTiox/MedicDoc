package Scenes;

import Containers.CardsPanel;
import Data.Patient;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PatientScene extends Scene{
    private final Patient patient;


    public PatientScene(String title, CardLayout cardLayout, CardsPanel parentPanel, Patient patient) {
        super(title, cardLayout, parentPanel);
        UIManager.put("Button.font", new Font("Arial", Font.ITALIC, 40));
        this.setLayout(new BorderLayout());
        this.patient = patient;
        this.add(getPatientInfoArea(), BorderLayout.NORTH);
        this.add(documentationPanel(), BorderLayout.CENTER);
        this.add(setUpButtons(), BorderLayout.SOUTH);

    }

    private JTextArea getPatientInfoArea(){
        JTextArea patientInfoArea;
        @SuppressWarnings("SpellCheckingInspection") String patientInfo = """
                
                        Imię i nazwisko: %s %s
                        Data urodzenia: %s     Wiek: %d
                        Miejsce zamieszkania: %s
                        PESEL: %s
                        Numer telefonu: %s    E-mail: %s
                ------------------------------------------------------------------------------
                """.formatted(patient.name, patient.lastName, patient.birthDate, getAge(), patient.address,
                patient.PESEL, patient.phoneNumber, patient.email);
        patientInfoArea = new JTextArea(patientInfo);
        patientInfoArea.setFont(new Font("Arial", Font.BOLD, 30));
        patientInfoArea.setEditable(false);
        patientInfoArea.setOpaque(false);

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
        documentationArea.setLineWrap(true);
        documentationArea.setWrapStyleWord(true);
        JScrollPane documentationPanel = new JScrollPane(documentationArea);
        documentationPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        documentationPanel.setOpaque(false);
        documentationPanel.getViewport().setOpaque(false);

        return documentationPanel;
    }

    private JPanel setUpButtons(){
        JPanel panel = new JPanel(new BorderLayout());
        @SuppressWarnings("SpellCheckingInspection")
        JButton updateButton = new JButton("Dodaj dokumentacje");
        updateButton.addActionListener(e ->{
            new newDocumentationScene("UPDATE-DOCUMENTATION", cardLayout, parentPanel, patient);
            cardLayout.show(parentPanel, "UPDATE-DOCUMENTATION");
        });
        JButton saveButton = new JButton("Zapisz");
        saveButton.addActionListener(e ->{
            if(!PatientsListScene.patientsList.contains(patient)) {
                PatientsListScene.patientsList.add(patient);
            }
            new PatientsListScene("PATIENTS_LIST", cardLayout, parentPanel);
            cardLayout.show(parentPanel, "PATIENTS_LIST");
        });

        panel.add(updateButton, BorderLayout.WEST);
        panel.add(saveButton, BorderLayout.EAST);
        panel.setOpaque(false);

        return panel;
    }


}
