package Scenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddingPatientScene extends Scene{

    @SuppressWarnings("SpellCheckingInspection")
    private JLabel[] labelsFields = new JLabel[]{
            new JLabel("Imię"),
            new JLabel("Nazwisko"),
            new JLabel("Adress zamieszkania"),
            new JLabel("Data Urodzenia"),
            new JLabel("PESEL"),
            new JLabel("Numer telefonu"),
            new JLabel("Dokumentacja")
    };

    private JTextField[] textField = new JTextField[]{
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
    };


    public AddingPatientScene(String title, CardLayout cardLayout, JPanel parentPanel) {
        super(title, cardLayout, parentPanel);
        AddingPatientScene test = this;

        setUpTextFields();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "enter");
        getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Patient newPatient =  new Patient(textField[0].getText(), textField[1].getText(), textField[2].getText(),
                        textField[3].getText(), textField[4].getText(), textField[5].getText(), textField[6].getText());
                PatientsListScene.patientsList.add(newPatient);
                JOptionPane.showMessageDialog(test, newPatient.toString());
            }
        });


    }

    private void setUpTextFields(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        int index = 0;
        for(JLabel label : labelsFields){
            gbc.gridx = 0;
            this.add(label, gbc);
            gbc.gridx++;
            this.add(textField[index], gbc);
            gbc.gridy++;
            index++;
        }
    }
}
