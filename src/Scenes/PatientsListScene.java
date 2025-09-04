package Scenes;


import Containers.CardsPanel;
import Containers.MainWindow;
import Data.Patient;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PatientsListScene extends Scene {

    public static List<Patient> patientsList = new ArrayList<>();
    private JPanel panel;

    public PatientsListScene(String title, CardLayout cardLayout, CardsPanel parentPanel) {
        super(title, cardLayout, parentPanel);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 30));
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = patientsListPanel();
        this.add(scrollPane);
        Collections.sort(patientsList);
        setUpButtons();
    }

    private JScrollPane patientsListPanel(){
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 40));
        panel.setOpaque(false);
        int n = patientsList.size()/30 + 1;
        panel.setPreferredSize(new Dimension(MainWindow.screenWidth, n*MainWindow.screenHeight));
        panel.setMaximumSize(new Dimension(MainWindow.screenWidth, Integer.MAX_VALUE));
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        JScrollPane patientsListPanel = new JScrollPane(panel);
        patientsListPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        patientsListPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        patientsListPanel.setOpaque(false);
        patientsListPanel.getViewport().setOpaque(false);

        return patientsListPanel;
    }

    private void setUpButtons(){
        for(Patient patient : patientsList){
            new PatientScene(patient.patientSceneTitle, cardLayout, parentPanel, patient);
            String buttonLabel = "<html>%s %s<br> %s</html>".formatted(patient.lastName, patient.name, patient.PESEL);
            JButton patientButton = new  JButton(buttonLabel);
            int buttonWidth =(int) (MainWindow.screenWidth/4.5);
            int buttonHeight = MainWindow.screenHeight/10;
            Dimension buttonSize = new Dimension(buttonWidth,buttonHeight);
            patientButton.setPreferredSize(buttonSize);
            patientButton.setOpaque(false);
            patientButton.addActionListener(e -> cardLayout.show(parentPanel, patient.patientSceneTitle));
            panel.add(patientButton);
        }
    }
}

