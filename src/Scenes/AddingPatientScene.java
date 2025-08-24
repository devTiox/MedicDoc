package Scenes;

import Containers.CardsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddingPatientScene extends Scene{

    @SuppressWarnings("SpellCheckingInspection")
    private final JLabel[] labelsFields = new JLabel[]{
            new JLabel("Imię"),
            new JLabel("Nazwisko"),
            new JLabel("Adress zamieszkania"),
            new JLabel("Data Urodzenia"),
            new JLabel("PESEL"),
            new JLabel("Numer telefonu"),
            new JLabel("E-mail")
    };

    private final JTextField[] textField = new JTextField[]{
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20)
    };


    public AddingPatientScene(String title, CardLayout cardLayout, CardsPanel parentPanel) {
        super(title, cardLayout, parentPanel);
        setUpTextFields();
        addPatient(this);
    }

    private void addPatient(AddingPatientScene parentScreen){
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "enter");
        getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Patient newPatient =  new Patient(textField[0].getText(), textField[1].getText(), textField[2].getText(),
                        textField[3].getText(), textField[4].getText(), textField[5].getText(), textField[6].getText(),null);
                confirmationWindow(newPatient, parentScreen);
            }
        });
    }

    private void confirmationWindow(Patient newPatient, AddingPatientScene parentScreen){
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 30));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 20));
        @SuppressWarnings("SpellCheckingInspection")
        int result = JOptionPane.showConfirmDialog(parentScreen,
                newPatient + "\nCzy wszystko się zgadza",
                "Potwierdzenie",
                JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION){
            new DocumentationScene("DOCUMENTATION_SCENE", cardLayout, parentPanel);
            cardLayout.show(parentPanel, "DOCUMENTATION_SCENE");
//            PatientsListScene.patientsList.add(newPatient);
        }
    }

    public void reset(){
        for(JTextField field : textField){
            field.setText("");
        }
    }

    private void setUpTextFields(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10, 20, 10);
        gbc.gridy = 0;
        JLabel label;
        JTextField field;
        for(int i = 0; i < 7; i++){
            gbc.gridx = 0;
            label = labelsFields[i];
            label.setFont(new Font("Arial", Font.BOLD, 30));
            this.add(label, gbc);
            gbc.gridx++;
            field = textField[i];
            field.setFont(new Font("Arial", Font.BOLD, 30));
            this.add(field, gbc);
            gbc.gridy++;
        }
    }
}
